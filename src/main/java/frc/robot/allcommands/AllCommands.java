package frc.robot.allcommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
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

    private double desiredShooterUpperRollerSpeedRPM = 0;
    private double desiredShooterLowerRollerSpeedRPM = 0;

    AllCommands(Intake intake, Launcher launcher, Pivot pivot, Shooter shooter) {
        this.intake = intake;
        this.launcher = launcher;
        this.pivot = pivot;
        this.shooter = shooter;

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
                .waitUntil(
                        () -> shooter.isAtSpeed(desiredShooterUpperRollerSpeedRPM, desiredShooterLowerRollerSpeedRPM))
                .andThen(launcherCMDs.release()).withName("shoot");
    }

    public Command stopAll() {
        return Commands.run(() -> {
            intake.stop();
            launcher.stop();
            pivot.stop();
            shooter.stop();
        }, intake, launcher, pivot, shooter).ignoringDisable(true).withName("stopAll");
    }

    public Command manualPivotController(DoubleSupplier angle) {
        return pivotCMDs.manualController(angle).withName("manualPivotController");
    }

    public Command manualShooterController(DoubleSupplier upperRollerVoltage, DoubleSupplier lowerRollerVoltage) {
        return shooterCMDs.manualController(upperRollerVoltage, lowerRollerVoltage).withName("manualShooterController");
    }

    public Command manualIntakeLauncherController(DoubleSupplier speed) {
        return Commands.parallel(
                intakeCMDs.manualController(() -> speed.getAsDouble() * INTAKE_HORIZONTAL_SPEED_MULTIPLAYER,
                        () -> speed.getAsDouble() * INTAKE_VERTICAL_SPEED_MULTIPLAYER),
                launcherCMDs.manualController(() -> speed.getAsDouble() * LAUNCHER_SPEED_MULTIPLAYER))
                .withName("manualIntakeLauncherController");
    }

    public Command getReadyToShoot(DoubleSupplier angle, DoubleSupplier upperRollerSpeed,
            DoubleSupplier lowerRollerSpeed) {
        return Commands.parallel(
                pivotCMDs.moveToAngle(angle),
                shooterCMDs.reachSpeed(upperRollerSpeed, lowerRollerSpeed)).withName("getReadyToShoot");
    }

    public Command getReadyToShootSubwoofer() {
        return getReadyToShoot(
                () -> GetReadyToShoot.SUBWOOFER_PIVOT_ANGLE,
                () -> GetReadyToShoot.SUBWOOFER_UPPER_ROLLER_SPEED,
                () -> GetReadyToShoot.SUBWOOFER_LOWER_ROLLER_SPEED).withName("getReadyToShootSubwoofer");
    }

    public Command getReadyToShootAmp() {
        return getReadyToShoot(
                () -> GetReadyToShoot.AMP_PIVOT_ANGLE,
                () -> GetReadyToShoot.AMP_UPPER_ROLLER_SPEED,
                () -> GetReadyToShoot.AMP_LOWER_ROLLER_SPEED).withName("getReadyToShootAmp");
    }
    
    public Command getReadyToShootTest() {
        return getReadyToShoot(
                () -> GetReadyToShoot.AMP_PIVOT_ANGLE,
                () -> GetReadyToShoot.AMP_UPPER_ROLLER_SPEED,
                () -> GetReadyToShoot.AMP_LOWER_ROLLER_SPEED).withName("getReadyToShootTest");
    }
}
