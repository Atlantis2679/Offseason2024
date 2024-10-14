// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.pivot;

/** Add your docs here. */
public class PivotConstants {
    // Sim
    public static final double PIVOT_JKG_METERS_SQUARED = 10;
    public static final double JOINT_GEAR_RATIO = 50;
    public static final double PIVOT_TURNING_MIN_DEGREES = 0;
    public static final double PIVOT_TURNING_MAX_DEGREES = 100;

    // Angle PID
    public static final double KP = 1;
    public static final double KI = 0;
    public static final double KD = 0;

    public static final int CURRENT_LIMIT_AMPS = 25;

    public class IsAtAngle {
        public static final double MAX_PIVOT_ANGLE_DEVAITION = 10;
        public static final double MIN_PIVOT_ANGLE_DEVAITION = -10;
    }

    public static final double PIVOT_MAX_VELOCITY_DEG_PER_SEC = 90;
    public static final double PIVOT_MAX_ACCELERATION_DEG_PER_SEC = 90;
    
    public static final double UPPER_BOUND_WRAP = 180;

    public static final double PIVOT_ANGLE_OFFSET_DEGREES = 0.5;

    public static final double KS = 0;
    // We suggest to keep KS at 0
    public static final double KA = 3;
    public static final double KV = 2;
    public static final double KG = 5;

    public static final double SIM_KS = 0;
    // We suggest to keep KS at 0
    public static final double SIM_KA = 0;
    public static final double SIM_KV = 0.035;
    public static final double SIM_KG = 3.355;

    public static final double PIVOT_VOLTAGE_LIMIT = 10;

    public static final double MANUAL_SPEED_MULTIPLIER = 10;
}
