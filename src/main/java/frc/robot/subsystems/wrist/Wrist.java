// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.wrist;


import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.logfields.LogFieldsTable;
import frc.lib.tuneables.TuneableBuilder;
import frc.lib.tuneables.extensions.TuneableArmFeedforward;
import frc.lib.tuneables.extensions.TuneableTrapezoidProfile;
import frc.robot.Robot;
import frc.robot.subsystems.wrist.io.WristIO;
import frc.robot.subsystems.wrist.io.WristIOSparkMax;
import frc.robot.utils.PrimitiveRotationalSensorHelper;
import frc.robot.subsystems.wrist.WristConstants.IsAtAngle;
import frc.robot.subsystems.wrist.io.WristIOSim;
import static frc.robot.subsystems.wrist.WristConstants.*;

public class Wrist extends SubsystemBase {
  private final LogFieldsTable fieldsTable = new LogFieldsTable(getName());

  private final WristIO io = Robot.isSimulation() ? new WristIOSim(fieldsTable) : new WristIOSparkMax(fieldsTable);

  private final PrimitiveRotationalSensorHelper wristAngleDegreesHelper;

  private final TuneableTrapezoidProfile trapezoidProfile = new TuneableTrapezoidProfile(
      new TrapezoidProfile.Constraints(WRIST_MAX_VELOCITY_DEG_SECOND, WRIST_MAX_ACCELERATION_DEG_SECOND));

  private final PIDController wristPIDcontroller = new PIDController(KP, KI, KD);

  private final TuneableArmFeedforward feedForwardWrist = Robot.isSimulation()
      ? new TuneableArmFeedforward(SIM_KA, SIM_KG, SIM_KV, SIM_KA)
      : new TuneableArmFeedforward(KS, KG, KV, KA);

  /** Creates a new Wrist. */
  public Wrist() {
    fieldsTable.update();
    wristAngleDegreesHelper = new PrimitiveRotationalSensorHelper(io.wristAngleDegrees.getAsDouble(),
        WRIST_ANGLE_OFFSET_DEGREES);
    wristAngleDegreesHelper.enableContinousWrap(UPPER_BOUND_WRAP, 360);
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
    wristAngleDegreesHelper.update(io.wristAngleDegrees.getAsDouble());
    fieldsTable.recordOutput("Wrist Angle Degrees", getWristAngleDegrees());
  }

  public void setWristVoltage(double voltage) {
    fieldsTable.recordOutput("Demand Voltage", voltage);
    voltage = MathUtil.clamp(voltage, -WRIST_VOLTAGE_LIMIT, WRIST_VOLTAGE_LIMIT);
    fieldsTable.recordOutput("Real voltage", voltage);
    io.setVoltage(voltage);
  }

  public double getAbsoluteAngle() {
    return wristAngleDegreesHelper.getAngle();
  }

  public double calculateFeedforward(double wristDesiredAngleDegrees, double wristDesiredVelocity, boolean usePID) {
    fieldsTable.recordOutput("desired angle degrees", wristDesiredAngleDegrees);
    double voltage = feedForwardWrist.calculate(Math.toRadians(wristDesiredAngleDegrees), wristDesiredVelocity);

    if(usePID) {
      voltage += wristPIDcontroller.calculate(getAbsoluteAngle(), wristDesiredAngleDegrees);
    }
    
    fieldsTable.recordOutput("feedforward result", voltage);
    return voltage;
  }

  public void resetPID() {
    wristPIDcontroller.reset();
  }

  public TrapezoidProfile.State calculateTrapezoidProfile(double time, TrapezoidProfile.State initialState, TrapezoidProfile.State goalState){
    TrapezoidProfile.State state = trapezoidProfile.calculate(time, initialState, goalState);
    fieldsTable.recordOutput("desired state", state.position);
    fieldsTable.recordOutput("desired velocity", state.velocity);

    return state;
  }

  public boolean isAtAngle(double desiredAngle) {
    if(desiredAngle - getAbsoluteAngle() < IsAtAngle.MAX_WRIST_ANGLE_DEVAITION && desiredAngle - getAbsoluteAngle() > IsAtAngle.MIN_WRIST_ANGLE_DEVAITION) {
      return true;
    }
    return false;
  }

  public void stop() {
    setWristVoltage(0);
  }

  public void initialTuneable(TuneableBuilder builder) {
    builder.addChild("Wrist PID", wristPIDcontroller);

    builder.addChild("Wrist feedforward", feedForwardWrist);

    builder.addChild("Trapezoid profile", trapezoidProfile);

    builder.addChild("wrist angle degrees", wristAngleDegreesHelper);  
  }
}