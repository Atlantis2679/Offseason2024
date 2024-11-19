package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.lib.tuneables.TuneablesManager;
import frc.lib.tuneables.extensions.TuneableCommand;
import frc.robot.allcommands.AllCommands;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.launcher.Launcher;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.swerve.Swerve;
import frc.robot.subsystems.swerve.SwerveCommands;
import frc.robot.utils.NaturalXboxController;

public class RobotContainer {
        // private final Swerve swerve = new Swerve();
        private final Intake intake = new Intake();
        private final Launcher launcher = new Launcher();
        private final Pivot pivot = new Pivot();
        private final Shooter shooter = new Shooter();

        private final AllCommands allCommands = new AllCommands(intake, launcher, pivot, shooter);

        private final NaturalXboxController driverController = new NaturalXboxController(
                        RobotMap.Controllers.DRIVER_PORT);

        private final NaturalXboxController operatorController = new NaturalXboxController(
                        RobotMap.Controllers.DRIVER_PORT);

        // private final SwerveCommands swerveCommands = new SwerveCommands(swerve);

        public RobotContainer() {
                // new Trigger(DriverStation::isDisabled)
                // .onTrue(Commands.parallel(allCommands.stopAll(), swerveCommands.stop()));

                // configureDriverBindings();
                configureOperetorBindings();
        }
        /*
         * private void configureDriverBindings() {
         * TuneableCommand driveCommand = swerveCommands.driverController(
         * () -> driverController.getLeftY(),
         * () -> driverController.getLeftX(),
         * () -> driverController.getRightX(),
         * driverController.leftBumper().negate()::getAsBoolean,
         * driverController.rightBumper()::getAsBoolean);
         * 
         * swerve.setDefaultCommand(driveCommand);
         * TuneablesManager.add("Swerve/drive command", driveCommand.fullTuneable());
         * driverController.a().onTrue(new InstantCommand(swerve::resetYaw));
         * driverController.x().onTrue(swerveCommands.xWheelLock());
         * 
         * TuneablesManager.add("Swerve/modules control mode",
         * swerveCommands.controlModules(
         * driverController::getLeftX,
         * driverController::getLeftY,
         * driverController::getRightY).fullTuneable());
         * 
         * }
         */

        private void configureOperetorBindings() {/*
                                                   * pivot.setDefaultCommand(allCommands.pivotReadyToCollect());
                                                   * operatorController.a().whileTrue(allCommands.collectToLauncher());
                                                   * operatorController.b().whileTrue(allCommands.shoot());
                                                   * operatorController.povUp().whileTrue(allCommands.
                                                   * getReadyToShootSubwoofer());
                                                   * operatorController.povDown().whileTrue(allCommands.
                                                   * getReadyToShootAmp());
                                                   * operatorController.povLeft().whileTrue(allCommands.
                                                   * getReadyToShootTuneable());
                                                   * operatorController.leftBumper().whileTrue(allCommands.stopAll());
                                                   */
                operatorController.rightBumper().whileTrue(Commands.parallel(
                                allCommands.manualIntakeLauncherController(() -> -operatorController.getLeftY())));/*
                                                                                                                    * allCommands
                                                                                                                    * .
                                                                                                                    * manualPivotController
                                                                                                                    * ((
                                                                                                                    * )
                                                                                                                    * ->
                                                                                                                    * -operatorController
                                                                                                                    * .
                                                                                                                    * getRightY
                                                                                                                    * ()
                                                                                                                    * ),
                                                                                                                    * allCommands
                                                                                                                    * .
                                                                                                                    * manualShooterController
                                                                                                                    * (
                                                                                                                    * operatorController
                                                                                                                    * ::
                                                                                                                    * getRightTriggerAxis,
                                                                                                                    * operatorController
                                                                                                                    * ::
                                                                                                                    * getLeftTriggerAxis
                                                                                                                    * ))
                                                                                                                    * );
                                                                                                                    */
        }

        public Command getAutonomousCommand() {
                return Commands.print("No autonomous command configured");
        }

        /*
         * @SuppressWarnings("unused")
         * private Trigger isOutOfThreshold(double value, double threshold) {
         * return new Trigger(() -> (value > Math.abs(threshold)));
         * }
         */
}
