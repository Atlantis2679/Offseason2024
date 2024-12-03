package frc.robot.allcommands;

public class AllCommandsConstants {
    public static final double PIVOT_ANGLE_FOR_INTAKE = -25;

    public static final double DELIVERY_PIVOT_ANGLE = 10;
    public static final double DELIVERY_LAUNCHER_SPEED = -1;

    public static final double LAUNCHER_SHOOT_SPEED = 1;

    public static final double LAUNCHER_COLLECT_SPEED = 0.5;

    public static class ManualController {
        public static final double INTAKE_VERTICAL_SPEED_MULTIPLAYER = 1;
        public static final double INTAKE_HORIZONTAL_SPEED_MULTIPLAYER = 1;
        public static final double LAUNCHER_SPEED_MULTIPLAYER = 1;
        public static final int SHOOTER_VOLTAGE_MULTIPLAYER = 12;
    }

    public static class GetReadyToShoot {
        public static final double SUBWOOFER_PIVOT_ANGLE = -24;
        public static final double SUBWOOFER_UPPER_ROLLER_SPEED = 2000;
        public static final double SUBWOOFER_LOWER_ROLLER_SPEED = 2000;

        public static final double AMP_PIVOT_ANGLE = 62;
        public static final double AMP_UPPER_ROLLER_SPEED = 1200;
        public static final double AMP_LOWER_ROLLER_SPEED = 500;

        public static final double TUNEABLE_PIVOT_ANGLE = 0;
        public static final double TUNEABLE_UPPER_ROLLER_SPEED = 0;
        public static final double TUNEABLE_LOWER_ROLLER_SPEED = 0;
    }
}
