// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter;

import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;

/** Add your docs here. */
public final class ShooterConstants {
    public static final int MAX_VOLTAGE = 12;

    public static final int ROLLER_CURRENT_LIMIT = 30;

    public static final int kP = 1;
    public static final int kD = 0;
    public static final int kI = 0;

    // sim
    public static final double GERAING = 1;
    public static final double kFlywheelMomentOfInertia = 0.00032; // kg * m^2

    public static final LinearSystem<N1, N1, N1> LINEAR_SYSTEM = LinearSystemId.createFlywheelSystem(DCMotor.getNEO(1),
            kFlywheelMomentOfInertia, GERAING);
}