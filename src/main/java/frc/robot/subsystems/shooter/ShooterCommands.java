package frc.robot.subsystems.shooter;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;

import static frc.robot.subsystems.shooter.ShooterConstants.*;

public class ShooterCommands {
    private final Shooter shooter;

    public ShooterCommands(Shooter shooter) {
        this.shooter = shooter;
    }

    public Command reachSpeed(DoubleSupplier targetUpperSpeedRPM, DoubleSupplier targetLowerSpeedRPM) {
        shooter.resetPID();
        return shooter.run(() -> {
            shooter.setVoltage(
                    shooter.calculateVoltageForUpperSpeedRPM(targetUpperSpeedRPM.getAsDouble()),
                    shooter.calculateLowerSpeedToVoltage(targetLowerSpeedRPM.getAsDouble()));
        }).finallyDo(shooter::stop).withName("shooterReachSpeed");
    }

    public Command manualController(DoubleSupplier upperRoller, DoubleSupplier lowerRoller) {
        return shooter.run(() -> {
            shooter.setVoltage(upperRoller.getAsDouble() * MAX_VOLTAGE, lowerRoller.getAsDouble() * MAX_VOLTAGE);
        }).finallyDo(shooter::stop).withName("shooterManualController");
    }
}