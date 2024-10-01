// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;

/** Add your docs here. */
public class ShooterCommands {
    private final Shooter shooter;

    public ShooterCommands(Shooter shooter) {
        this.shooter = shooter;
    }

    public void reachSpeed(double targetSpeedRPM, PIDController pid) {
        targetSpeedRPM = MathUtil.clamp(ShooterConstants.MAX_VOLTAGE, -ShooterConstants.MAX_VOLTAGE, targetSpeedRPM);
        shooter.setSpeed(pid.calculate(shooter.getUpperRollerSpeedRPM(), targetSpeedRPM),
                pid.calculate(shooter.getLowerRollerSpeedRPM(), targetSpeedRPM));
    }
}
