package frc.robot.subsystems.shooter;

import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;

public final class ShooterConstants {
    public static final int MAX_VOLTAGE = 7;
    public static final double SPEED_TOLERANCE = 50;
    
    public static final int ROLLERS_CURRENT_LIMIT = 30;
    
    public static final double kP = 0.002;
    public static final double kD = 0;
    public static final double kI = 0;
    
    public static final double kS = 0;
    public static final double kV = 0.0022;
    public static final double kA = 0;

    // sim
    public static final double GERAING = 1;
    public static final double kFlywheelMomentOfInertia = 0.00032; // kg * m^2

    public static final LinearSystem<N1, N1, N1> LINEAR_SYSTEM = LinearSystemId.createFlywheelSystem(DCMotor.getNEO(1),
            kFlywheelMomentOfInertia, GERAING);

    public static final double kPSim = 1;
    public static final double kISim = 0;
    public static final double kDSim = 0;

    public static final double kSSim = 0;
    public static final double kVSim = 1;
    public static final double kASim = 0;
}