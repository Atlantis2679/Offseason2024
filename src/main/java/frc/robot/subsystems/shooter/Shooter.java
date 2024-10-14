// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.logfields.LogFieldsTable;
import frc.robot.subsystems.shooter.io.ShooterIO;
import frc.robot.subsystems.shooter.io.ShooterIOSim;
import frc.robot.subsystems.shooter.io.ShooterIOSparkMax;
import static frc.robot.subsystems.shooter.ShooterConstants.*;

public class Shooter extends SubsystemBase {
  private final LogFieldsTable fieldsTable = new LogFieldsTable(getName());
  private ShooterIO io = RobotBase.isReal() ? new ShooterIOSparkMax(fieldsTable) : new ShooterIOSim(fieldsTable);
  private PIDController pid = new PIDController(RobotBase.isReal() ? kP : kPSim, RobotBase.isReal() ? kI : kISim, RobotBase.isReal() ? kD : kDSim)

  public Shooter()
  {
    fieldsTable.update();
  }

  @Override
  public void periodic() {
  }

  public void setVoltage(double upperRollerVoltage, double lowerRollerVoltage) {
    fieldsTable.recordOutput("upperRollervoltage set", upperRollerVoltage);
    fieldsTable.recordOutput("lowerRollervoltage set", upperRollerVoltage);
    io.setUpperRollerVoltage(
        MathUtil.clamp(upperRollerVoltage, -ShooterConstants.MAX_VOLTAGE, ShooterConstants.MAX_VOLTAGE));
    io.setLowerRollerVoltage(
        MathUtil.clamp(lowerRollerVoltage, -ShooterConstants.MAX_VOLTAGE, ShooterConstants.MAX_VOLTAGE));
    fieldsTable.recordOutput("upperRollerSpeed", getUpperRollerSpeedRPM());
    fieldsTable.recordOutput("lowerRollerSpeed", getLowerRollerSpeedRPM());
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

  public boolean isAtSpeed(double targetSpeedRPM) {
    return (getUpperRollerSpeedRPM() >= targetSpeedRPM - 10 && getUpperRollerSpeedRPM() <= targetSpeedRPM + 10)
        && (getLowerRollerSpeedRPM() >= targetSpeedRPM - 10 && getLowerRollerSpeedRPM() <= targetSpeedRPM + 10);
  }

  public double calculateVoltageToSpeed(double currentSpeed, double targetSpeedRPM) {
    return pid.calculate(currentSpeed, targetSpeedRPM) / 473;
  }

  public void resetPID() {
    pid.reset();
  }
}