// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.logfields.LogFieldsTable;
import frc.robot.subsystems.shooter.io.ShooterIO;
import frc.robot.subsystems.shooter.io.ShooterIOSparkMax;

public class Shooter extends SubsystemBase {
  private final LogFieldsTable fieldsTable = new LogFieldsTable(getName());

  private final ShooterIO io = new ShooterIOSparkMax(fieldsTable);

  public Shooter() {
    fieldsTable.update();
  }

  @Override
  public void periodic() {
  }

  public void setVoltage(double upperRollerSpeed, double lowerRollerSpeed) {
    io.setUpperRollerVoltage(MathUtil.clamp(upperRollerSpeed, -ShooterConstants.MAX_VOLTAGE, ShooterConstants.MAX_VOLTAGE));
    io.setLowerRollerVoltage(MathUtil.clamp(lowerRollerSpeed, -ShooterConstants.MAX_VOLTAGE, ShooterConstants.MAX_VOLTAGE));
  }

  public void stop() {
    setVoltage(0, 0);
  }

  public double getUpperRollerSpeedRPM() {
    return io.upperRollerSpeedRPM.getAsDouble();
  }

  public double getLowerRollerSpeedRPM() {
    return io.lowerRollerSpeedRPM.getAsDouble();
  }


}
