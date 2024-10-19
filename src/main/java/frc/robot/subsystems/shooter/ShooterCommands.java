package frc.robot.subsystems.shooter;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

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
            Logger.recordOutput("Shooter/upperRollerTargetSpeedRPM", targetUpperSpeedRPM.getAsDouble());
            Logger.recordOutput("Shooter/lowerRollerTargetSpeedRPM", targetLowerSpeedRPM.getAsDouble());
            shooter.setVoltage(
                    shooter.calculateVoltageForUpperSpeedRPM(shooter.getUpperRollerSpeedRPM(),
                            targetUpperSpeedRPM.getAsDouble()),
                    shooter.calculateLowerSpeedToVoltage(shooter.getLowerRollerSpeedRPM(),
                            targetLowerSpeedRPM.getAsDouble()));
        }).withName("shooterReachSpeed");
    }

    public Command manualController(DoubleSupplier upperRoller, DoubleSupplier lowerRoller) {
        return shooter.run(() -> {
            shooter.setVoltage(upperRoller.getAsDouble() * MAX_VOLTAGE, lowerRoller.getAsDouble() * MAX_VOLTAGE);
        }).withName("shooterManualController");
    }
}