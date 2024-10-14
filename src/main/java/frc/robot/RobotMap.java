package frc.robot;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;

public class RobotMap {
    public static final ModuleType POWER_DISTRIBUATION_TYPE = ModuleType.kRev;

    public static final SPI.Port NAVX_PORT = SPI.Port.kMXP;
    public static final String FRONT_LEFT_CAMERA_NAME = "FrontLeftCam";
    public static final String FRONT_RIGHT_CAMERA_NAME = "FrontRightCam";

    public static class Controllers {
        public static final int DRIVER_PORT = 0;
        public static final int OPERATOR_PORT = 1;
    }
       
    public static class CANBUS {
        public static final int UPPER_ROLLER_ID = 16;
        public static final int LOWER_ROLLER_ID = 17;

        public final static int INTAKE_RIGHT_VERTICAL_ROLLER_ID = 12;
        public final static int INTAKE_LEFT_VERTICAL_ROLLER_ID = 13;
        public final static int INTAKE_HORIZONTAL_ROLLER_ID = 11;
      
        public final static int PIVOT_LEFT_MOTOR_ID = 0;
        public final static int PIVOT_RIGHT_MOTOR_ID = 0;
        public final static int LAUNCHER_MOTOR_ID = 0;
      
        public class ModuleFL {
            public final static int DRIVE_MOTOR_ID = 20;
            public final static int ANGLE_MOTOR_ID = 21;
            public final static int ENCODER_ID = 50;
        }
    
        public class ModuleFR {
            public final static int DRIVE_MOTOR_ID = 26;
            public final static int ANGLE_MOTOR_ID = 27;
            public final static int ENCODER_ID = 53;
        }
    
        public class ModuleBL {
            public final static int DRIVE_MOTOR_ID = 24;
            public final static int ANGLE_MOTOR_ID = 25;
            public final static int ENCODER_ID = 52;
        }
    
        public class ModuleBR {
            public final static int DRIVE_MOTOR_ID = 22;
            public final static int ANGLE_MOTOR_ID = 23;
            public final static int ENCODER_ID = 51;
        }


    }
  
    public static class DIO {
        public final static int PIVOT_ENCODER_ID = 0;
        public static final int BEAM_BREAK_ID = 0;
    }
}
