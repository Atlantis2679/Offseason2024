// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.logfields.LogFieldsTable;
import frc.robot.subsystems.intake.io.IntakeIO;
import frc.robot.subsystems.intake.io.IntakeIOSparkMax;

public class Intake extends SubsystemBase {

  private final LogFieldsTable fieldsTable = new LogFieldsTable(getName());
  private final IntakeIO intakeIO = new IntakeIOSparkMax(fieldsTable);
  
  /** Creates a new Intake. */
  public Intake() {

    fieldsTable.update();
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }

  public void setRollerSpeed(double speed){
    intakeIO.setRollerSpeed(speed);
  }

  public void setRollerSpeedVoltage(double voltage){
    intakeIO.setRollerSpeed(voltage);
  }

  public void stop(){
    intakeIO.stop();
  }
}
