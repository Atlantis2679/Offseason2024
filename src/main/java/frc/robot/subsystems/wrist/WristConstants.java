// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.wrist;

/** Add your docs here. */
public class WristConstants {
    // Sim
    public static final double WRIST_JKG_METERS_SQUARED = 1;
    public static final double JOINT_GEAR_RATIO = 1;
    public static final double WRIST_TURNING_MIN_DEGREES = 0;
    public static final double WRIST_TURNING_MAX_DEGREES = 100;

    // Angle PID
    public static final double KP = 2;
    public static final double KI = 0.2;
    public static final double KD = 1;

    public class IsAtAngle {
        public static final double MAX_WRIST_ANGLE_DEVAITION = 10;
        public static final double MIN_WRIST_ANGLE_DEVAITION = -10;
    }

    public static final double WRIST_MAX_VELOCITY_DEG_SECOND = 10;
    public static final double WRIST_MAX_ACCELERATION_DEG_SECOND = 10;
    
    public static final double UPPER_BOUND_WRAP = 0;

    public static final double WRIST_ANGLE_OFFSET_DEGREES = 0.5;

    public static final double KS = 0;
    // We suggest to keep KS at 0
    public static final double KA = 3;
    public static final double KV = 2;
    public static final double KG = 5;

    public static final double SIM_KS = 0;
    // We suggest to keep KS at 0
    public static final double SIM_KA = 4;
    public static final double SIM_KV = 3;
    public static final double SIM_KG = 5;

    public static final double WRIST_VOLTAGE_LIMIT = 20;

    public static final double MANUAL_SPEED_MULTIPLIER = 1;
}