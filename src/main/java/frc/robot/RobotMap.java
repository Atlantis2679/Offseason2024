package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;

public class RobotMap {
    public static final ModuleType POWER_DISTRIBUATION_TYPE = ModuleType.kRev;

    public static class CANBUS {
        public final static int WRIST_LEFT_MOTOR_ID = 0;
        public final static int WRIST_RIGHT_MOTOR_ID = 0;
    }

     public static class DIO {
        public final static int WRIST_ENCODER_ID = 0;
    }
}
