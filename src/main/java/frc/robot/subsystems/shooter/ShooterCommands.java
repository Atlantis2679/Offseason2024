// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;

/** Add your docs here. */
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
        });
    }

    public Command manualController(DoubleSupplier upperRollerVoltage, DoubleSupplier lowerRollerVoltage) {
        return shooter.run(() -> {
            shooter.setVoltage(upperRollerVoltage.getAsDouble(), lowerRollerVoltage.getAsDouble());
        });
    }
}