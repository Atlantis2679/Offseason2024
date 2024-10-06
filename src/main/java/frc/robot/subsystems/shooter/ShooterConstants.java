// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter;

import edu.wpi.first.math.system.LinearSystem;

/** Add your docs here. */
public final class ShooterConstants {
    public static final int MAX_VOLTAGE = 0;

    public static final int ROLLER_CURRENT_LIMIT = 30;

    public static final int kP = 1;
    public static final int kD = 0;
    public static final int kI = 0;

    // sim
    public static final LinearSystem LINEAR_SYSTEM = new LinearSystem<>(null, null, null, null);
    public static final double GERAING = 0;

}
