// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;

/** Add your docs here. */
public class ShooterCommands extends Command {
    private final Shooter shooter;

    public ShooterCommands(Shooter shooter) {
        this.shooter = shooter;
        this.addRequirements(shooter);
    }

    public Command reachSpeed(double targetSpeedRPM, PIDController pid) {
        return shooter.run(() -> {
            shooter.setVoltage(
                    pid.calculate(shooter.getUpperRollerSpeedRPM(), targetSpeedRPM) / 437,
                    pid.calculate(shooter.getLowerRollerSpeedRPM(), targetSpeedRPM) / 437);
        }).until(() -> ((shooter.getUpperRollerSpeedRPM() >= targetSpeedRPM - 10
                && shooter.getUpperRollerSpeedRPM() <= targetSpeedRPM + 10)
                && (shooter.getLowerRollerSpeedRPM() >= targetSpeedRPM - 10
                        && shooter.getLowerRollerSpeedRPM() <= targetSpeedRPM + 10)));
    }

}