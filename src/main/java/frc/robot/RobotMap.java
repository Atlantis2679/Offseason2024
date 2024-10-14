package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;

public class RobotMap {
    public static final ModuleType POWER_DISTRIBUATION_TYPE = ModuleType.kRev;
    
    public static class CANBUS {
        public final static int INTAKE_RIGHT_VERTICAL_ROLLER_ID = 12;
        public final static int INTAKE_LEFT_VERTICAL_ROLLER_ID = 13;
        public final static int INTAKE_HORIZONTAL_ROLLER_ID = 11;
    }
}
