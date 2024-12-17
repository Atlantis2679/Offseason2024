package frc.robot;

import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import com.pathplanner.lib.util.GeometryUtil;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.lib.tuneables.Tuneable;
import frc.lib.tuneables.TuneablesManager;
import frc.lib.tuneables.extensions.TuneableCommand;
import frc.robot.allcommands.AllCommands;
import frc.robot.allcommands.ShootingCalculator;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.launcher.Launcher;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.swerve.Swerve;
import frc.robot.subsystems.swerve.SwerveCommands;
import frc.robot.utils.NaturalXboxController;

public class RobotContainer {
        private final Swerve swerve = new Swerve();
        private final Intake intake = new Intake();
        private final Launcher launcher = new Launcher();
        private final Pivot pivot = new Pivot();
        private final Shooter shooter = new Shooter();

        private final ShootingCalculator shootingCalculator = new ShootingCalculator();
        private final AllCommands allCommands = new AllCommands(intake, launcher,
                        pivot, shooter, shootingCalculator);
        
        private final NaturalXboxController driverController = new NaturalXboxController(
                        RobotMap.Controllers.DRIVER_PORT);

        private final NaturalXboxController operatorController = new NaturalXboxController(
                        RobotMap.Controllers.OPERATOR_PORT);

        private final SwerveCommands swerveCommands = new SwerveCommands(swerve);

        private final LoggedDashboardChooser<Pose2d> startPosChooser = new LoggedDashboardChooser<>("Start Position");
        private final Field2d field2d = new Field2d();

        private final LoggedDashboardChooser<Boolean> autoShootSpeaker = new LoggedDashboardChooser<>(
                        "auto shoot speaker");

        public RobotContainer() {
                new Trigger(DriverStation::isDisabled).whileTrue(Commands.parallel(allCommands.stopAll(),
                                swerveCommands.stop()));

                configureDriverBindings();
                configureOperetorBindings();

                SmartDashboard.putData("field2d", field2d);

                swerve.registerCallbackOnPoseUpdate(shootingCalculator::update);
                swerve.registerCallbackOnPoseUpdate((pose, isRedAlliance) -> {
                        field2d.setRobotPose(pose);
                        field2d.getObject("startPos").setPose(getStartPosition());
                });

                startPosChooser.addDefaultOption("subwoofer", new Pose2d(1.28, 5.55, Rotation2d.fromDegrees(0)));
                startPosChooser.addOption("next to amp", new Pose2d(0.45, 7.23, Rotation2d.fromDegrees(0)));
                startPosChooser.addOption("next to source", new Pose2d(0.45, 2.01, Rotation2d.fromDegrees(0)));
                startPosChooser.addOption("subwoofer source side",
                                new Pose2d(0.66, 4.45, Rotation2d.fromDegrees(-58.78)));
                startPosChooser.addOption("subwoofer amp side", new Pose2d(0.66, 6.66, Rotation2d.fromDegrees(58.78)));

                autoShootSpeaker.addDefaultOption("no", false);
                autoShootSpeaker.addOption("yes", true);
        }

        private void configureDriverBindings() {
                TuneableCommand driveCommand = swerveCommands.driverController(
                                () -> driverController.getLeftY(),
                                () -> driverController.getLeftX(),
                                () -> driverController.getRightX(),
                                driverController.leftBumper().negate()::getAsBoolean,
                                driverController.rightBumper()::getAsBoolean);

                swerve.setDefaultCommand(driveCommand);
                TuneablesManager.add("Swerve/drive command", driveCommand.fullTuneable());
                driverController.a().onTrue(new InstantCommand(swerve::resetYaw));
                driverController.x().onTrue(swerveCommands.xWheelLock());
                TuneableCommand rotateToSpeaker = swerveCommands
                                .rotateToAngle(shootingCalculator::getRobotYawDegreesCCW);
                driverController.y().whileTrue(rotateToSpeaker);
                TuneablesManager.add("Rotate to speaker", (Tuneable) rotateToSpeaker);

                TuneablesManager.add("Swerve/modules control mode",
                                swerveCommands.controlModules(
                                                driverController::getLeftX,
                                                driverController::getLeftY,
                                                driverController::getRightY).fullTuneable());
        }

        private void configureOperetorBindings() {
                pivot.setDefaultCommand(allCommands.pivotReadyToCollect());
                operatorController.a().whileTrue(allCommands.collectToLauncher());
                operatorController.b().whileTrue(allCommands.shoot());
                operatorController.y().whileTrue(allCommands.delivery());

                operatorController.povUp().whileTrue(allCommands.getReadyToShootSubwoofer());
                operatorController.povDown().whileTrue(allCommands.getReadyToShootAmp());
                operatorController.povLeft().whileTrue(allCommands.getReadyToShootVision());
                TuneableCommand tuneableReadyToShoot = allCommands.getReadyToShootTuneable();
                operatorController.povRight().whileTrue(tuneableReadyToShoot);
                TuneablesManager.add("ready to shoot command", (Tuneable) tuneableReadyToShoot);
                operatorController.leftBumper().whileTrue(allCommands.stopAll());
                operatorController.rightBumper().whileTrue(Commands.parallel(
                                allCommands.manualIntakeLauncherController(() -> operatorController.getLeftY()),
                                allCommands.manualPivotController(() -> operatorController.getRightY()),
                                allCommands.manualShooterController(operatorController::getSquaredLeftTriggerAxis,
                                                operatorController::getSquaredLeftTriggerAxis)));
        }

        private Pose2d getStartPosition() {
                Pose2d startPose = startPosChooser.get();
                if (startPose == null)
                        return new Pose2d(1.28, 5.55, Rotation2d.fromDegrees(0));
                return swerve.getIsRedAlliance()
                                ? GeometryUtil.flipFieldPose(startPosChooser.get())
                                : startPosChooser.get();
        }

        public Command getAutonomousCommand() {
                return Commands.runOnce(() -> swerve.resetPose(getStartPosition()))
                                .andThen(allCommands.autoShootSpeaker().unless(
                                                () -> autoShootSpeaker.get() != null ? !autoShootSpeaker.get() : true));
        }

        @SuppressWarnings("unused")
        private Trigger isOutOfThreshold(double value, double threshold) {
                return new Trigger(() -> (value > Math.abs(threshold)));
        }
}
