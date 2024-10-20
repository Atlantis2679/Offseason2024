package frc.robot.subsystems.pivot;

public class PivotConstants {
    // Angle PID
    public static final double KP = 0.15;
    public static final double KI = 0;
    public static final double KD = 0;

    public static final double KS = 0;
    public static final double KG = 0.5;
    public static final double KV = 0.04;
    public static final double KA = 0;

    public static final int CURRENT_LIMIT_AMPS = 35;

    public class IsAtAngle {
        public static final double ANGLE_TOLERANCE = 1.5;
    }

    public static final double PIVOT_MAX_VELOCITY_DEG_PER_SEC = 90;
    public static final double PIVOT_MAX_ACCELERATION_DEG_PER_SEC = 90;

    public static final double UPPER_BOUND_WRAP = 180;
    public static final double MIN_ANGLE_DEGREE = -24;
    public static final double MAX_ANGLE_DEGREE = 120;

    public static final double PIVOT_ANGLE_OFFSET_DEGREES = 121;

    public static final double SIM_KS = 0;
    public static final double SIM_KA = 0;
    public static final double SIM_KV = 0.035;
    public static final double SIM_KG = 0.355;

    public static final double PIVOT_VOLTAGE_LIMIT = 10;

    public static final double MANUAL_SPEED_MULTIPLIER = 7;

    // Sim
    public static final double PIVOT_JKG_METERS_SQUARED = 10;
    public static final double JOINT_GEAR_RATIO = 50;
    public static final double PIVOT_TURNING_MIN_DEGREES = -25;
    public static final double PIVOT_TURNING_MAX_DEGREES = 190;

}
