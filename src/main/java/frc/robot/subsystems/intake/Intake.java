// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.logfields.LogFieldsTable;
import frc.robot.Robot;
import frc.robot.subsystems.intake.io.IntakeIO;
import frc.robot.subsystems.intake.io.IntakeIOSim;
import frc.robot.subsystems.intake.io.IntakeIOSparkMax;

public class Intake extends SubsystemBase {

  private final LogFieldsTable fieldsTable = new LogFieldsTable(getName());
  private final IntakeIO intakeIO = Robot.isSimulation()
      ? new IntakeIOSim(fieldsTable)
      : new IntakeIOSparkMax(fieldsTable);
  private boolean shouldStop = true;

  public Intake() {

    fieldsTable.update();
  }

  @Override
  public void periodic() {
    if (shouldStop) {
      setRollerSpeed(0);
    }
  }

  public void setRollerSpeed(double speed) {
    shouldStop = false;
    intakeIO.setRollerSpeed(speed);
    fieldsTable.recordOutput("intake rollers speed", speed);
  }

  public void stop() {
    shouldStop = true;
    intakeIO.setRollerSpeed(0);
    fieldsTable.recordOutput("intake rollers speed", 0.0);
  }
}
