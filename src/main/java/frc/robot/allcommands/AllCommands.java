package frc.robot.allcommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.lib.tuneables.extensions.TuneableCommand;
import frc.lib.valueholders.DoubleHolder;
import frc.robot.allcommands.AllCommandsConstants.GetReadyToShoot;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.IntakeCommands;
import frc.robot.subsystems.launcher.Launcher;
import frc.robot.subsystems.launcher.LauncherCommands;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.subsystems.pivot.PivotCommands;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.ShooterCommands;

import static frc.robot.allcommands.AllCommandsConstants.*;

import java.util.function.DoubleSupplier;

public class AllCommands {
    private Intake intake;
    private Launcher launcher;
    private Pivot pivot;
    private Shooter shooter;

    private IntakeCommands intakeCMDs;
    private LauncherCommands launcherCMDs;
    private PivotCommands pivotCMDs;
    private ShooterCommands shooterCMDs;

    private double targetShooterUpperRollerRPM = 0;
    private double targetShooterLowerRollerRPM = 0;

    private double targetPivotAngle = 0;

    private final ShootingCalculator shootingCalculator;

    public AllCommands(Intake intake, Launcher launcher, Pivot pivot, Shooter shooter,
            ShootingCalculator shootingCalculator) {
        this.intake = intake;
        this.launcher = launcher;
        this.pivot = pivot;
        this.shooter = shooter;
        this.shootingCalculator = shootingCalculator;

        intakeCMDs = new IntakeCommands(this.intake);
        launcherCMDs = new LauncherCommands(this.launcher);
        pivotCMDs = new PivotCommands(this.pivot);
        shooterCMDs = new ShooterCommands(this.shooter);
    }

    public Command collectToLauncher() {
        return Commands.waitUntil(() -> pivot.isAtAngle(PIVOT_ANGLE_FOR_INTAKE))
                .andThen(Commands.deadline(launcherCMDs.load(), intakeCMDs.collect())).withName("collectToLauncher");
    }

    public Command shoot() {
        return Commands
                .waitUntil(() -> (shooter.isAtSpeed(targetShooterUpperRollerRPM, targetShooterLowerRollerRPM)
                        && pivot.isAtAngle(targetPivotAngle)))
                .andThen(launcherCMDs.release()).withName("shoot");
    }

    public Command stopAll() {
        return Commands.run(() -> {
            intake.stop();
            launcher.stop();
            pivot.stop();
            shooter.stop();
        }, intake, launcher, pivot, shooter).ignoringDisable(true)
                .withInterruptBehavior(InterruptionBehavior.kCancelIncoming).withName("stopAll");
    }

    public Command getReadyToShoot(DoubleSupplier angle, DoubleSupplier upperRollerSpeed,
            DoubleSupplier lowerRollerSpeed) {
        return Commands.parallel(
                Commands.run(() -> {
                    targetShooterUpperRollerRPM = upperRollerSpeed.getAsDouble();
                    targetShooterLowerRollerRPM = lowerRollerSpeed.getAsDouble();
                    targetPivotAngle = angle.getAsDouble();
                }),
                pivotCMDs.moveToAngle(angle),
                shooterCMDs.reachSpeed(upperRollerSpeed, lowerRollerSpeed)).withName("getReadyToShoot");
    }

    public Command getReadyToShootSubwoofer() {
        return getReadyToShoot(
                () -> GetReadyToShoot.SUBWOOFER_PIVOT_ANGLE,
                () -> GetReadyToShoot.SUBWOOFER_UPPER_ROLLER_SPEED,
                () -> GetReadyToShoot.SUBWOOFER_LOWER_ROLLER_SPEED)
                .withName("getReadyToShootSubwoofer");
    }

    public Command getReadyToShootAmp() {
        return getReadyToShoot(
                () -> GetReadyToShoot.AMP_PIVOT_ANGLE,
                () -> GetReadyToShoot.AMP_UPPER_ROLLER_SPEED,
                () -> GetReadyToShoot.AMP_LOWER_ROLLER_SPEED).withName("getReadyToShootAmp");
    }

    public Command getReadyToShootVision() {
        return getReadyToShoot(
                () -> shootingCalculator.getUpperRollerSpeedRPM(),
                () -> shootingCalculator.getUpperRollerSpeedRPM(),
                () -> shootingCalculator.getLowerRollerSpeedRPM()).withName("getReadyToShootAmp");
    }

    public TuneableCommand getReadyToShootTuneable() {
        return TuneableCommand.wrap((tuneableTable) -> {
            DoubleHolder angleHolder = tuneableTable.addNumber("angle", GetReadyToShoot.TUNEABLE_PIVOT_ANGLE);
            DoubleHolder upperRollerHolder = tuneableTable.addNumber("upper roller speed",
                    GetReadyToShoot.TUNEABLE_UPPER_ROLLER_SPEED);
            DoubleHolder lowerRollerHolder = tuneableTable.addNumber("lower roller speed",
                    GetReadyToShoot.TUNEABLE_LOWER_ROLLER_SPEED);
            return getReadyToShoot(angleHolder::get, upperRollerHolder::get, lowerRollerHolder::get)
                    .withName("getReadyToShootTuneable");
        });
    }

    public Command pivotReadyToCollect() {
        return pivotCMDs.moveToAngle(PIVOT_ANGLE_FOR_INTAKE).withName("pivotReadyToCollect");
    }

    public Command manualIntakeLauncherController(DoubleSupplier speed) {
        return Commands.parallel(
                intakeCMDs.manualController(
                        () -> speed.getAsDouble() * ManualController.INTAKE_HORIZONTAL_SPEED_MULTIPLAYER,
                        () -> speed.getAsDouble() * ManualController.INTAKE_VERTICAL_SPEED_MULTIPLAYER),
                launcherCMDs.manualController(() -> speed.getAsDouble() * ManualController.LAUNCHER_SPEED_MULTIPLAYER))
                .withName("manualIntakeLauncherController");
    }

    public Command manualPivotController(DoubleSupplier angle) {
        return pivotCMDs.manualController(angle);
    }

    public Command manualShooterController(DoubleSupplier upperRollerVoltagePercentage,
            DoubleSupplier lowerRollerVoltagePercentage) {
        return shooterCMDs.manualController(
                () -> upperRollerVoltagePercentage.getAsDouble() * ManualController.SHOOTER_VOLTAGE_MULTIPLAYER,
                () -> lowerRollerVoltagePercentage.getAsDouble() * ManualController.SHOOTER_VOLTAGE_MULTIPLAYER);
    }
}
