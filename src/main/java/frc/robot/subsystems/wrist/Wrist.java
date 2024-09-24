// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.wrist;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.logfields.LogFieldsTable;
import frc.robot.Robot;
import frc.robot.subsystems.wrist.io.WristIO;
import frc.robot.subsystems.wrist.io.WristIOSparkMax;

public class Wrist extends SubsystemBase{
    private final LogFieldsTable fieldsTable = new LogFieldsTable(getName());
    private final WristIO io = Robot.isSimulation()? new WristIOSim(fieldsTable) : new WristIOSparkMax(fieldsTable);
    
    private final PIDController wristPIDcontroller = new PIDController(KP, KI, KD);

  /** Creates a new Wrist. */
  public Wrist() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
