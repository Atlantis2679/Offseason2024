// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;

/** Add your docs here. */
public class ShooterCommands extends Command{
    private final Shooter shooter;

    public ShooterCommands(Shooter shooter) {
        this.shooter = shooter;
        this.addRequirements(shooter);
    }

    public Command reachSpeed(double targetSpeedRPM, PIDController pid) {
        return shooter.runOnce(() -> {
            shooter.setSpeed(
                pid.calculate(shooter.getUpperRollerSpeedRPM(), MathUtil.clamp(targetSpeedRPM, ShooterConstants.MAX_VOLTAGE, -ShooterConstants.MAX_VOLTAGE)),
                pid.calculate(shooter.getLowerRollerSpeedRPM(), MathUtil.clamp(targetSpeedRPM, ShooterConstants.MAX_VOLTAGE, -ShooterConstants.MAX_VOLTAGE)));
        });
    }
}
