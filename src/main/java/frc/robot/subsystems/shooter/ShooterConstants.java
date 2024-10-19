package frc.robot.subsystems.shooter;

import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;

public final class ShooterConstants {
    public static final int MAX_VOLTAGE = 12;

    public static final int ROLLERS_CURRENT_LIMIT = 30;

    public static final int kP = 0;
    public static final int kD = 0;
    public static final int kI = 0;

    public static final int kS = 0;
    public static final int kV = 0;
    public static final int kA = 0;

    // sim
    public static final double GERAING = 1;
    public static final double kFlywheelMomentOfInertia = 0.00032; // kg * m^2

    public static final LinearSystem<N1, N1, N1> LINEAR_SYSTEM = LinearSystemId.createFlywheelSystem(DCMotor.getNEO(1),
            kFlywheelMomentOfInertia, GERAING);

    public static final int kPSim = 1;
    public static final int kISim = 0;
    public static final int kDSim = 0;

    public static final int kSSim = 0;
    public static final int kVSim = 1;
    public static final int kASim = 0;
}