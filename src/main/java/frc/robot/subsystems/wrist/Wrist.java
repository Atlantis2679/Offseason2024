// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.wrist;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.logfields.LogFieldsTable;
import frc.lib.tuneables.Tuneable;
import frc.robot.Robot;
import frc.robot.subsystems.wrist.io.WristIO;
import frc.robot.subsystems.wrist.io.WristIOSparkMax;
import frc.robot.utils.PrimitiveRotationalSensorHelper;
import frc.robot.subsystems.wrist.WristConstants;
import frc.robot.subsystems.wrist.io.WristIOSim;

public class Wrist extends SubsystemBase{
    private final LogFieldsTable fieldsTable = new LogFieldsTable(getName());
    private final WristIO io = Robot.isSimulation()? new WristIOSim(fieldsTable) : new WristIOSparkMax(fieldsTable);
    private final PrimitiveRotationalSensorHelper wristAngleDegreesHelper;
    private final PIDController wristPIDcontroller = new PIDController(WristConstants.KP, WristConstants.KI, WristConstants.KD);

  /** Creates a new Wrist. */
  public Wrist() {
    wristAngleDegreesHelper.enableContinousWrap(WristConstants.UPPER_BOUND, 360);
  }
  
  public void setSpeed(double speed) {
    io.setSpeed(speed);
  }

  public double getWristAngleDegrees() {
    return io.wristAngleDegrees.getAsDouble();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
