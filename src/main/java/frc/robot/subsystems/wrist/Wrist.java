// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.wrist;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.logfields.LogFieldsTable;
import frc.lib.tuneables.Tuneable;
import frc.lib.tuneables.TuneableBuilder;
import frc.lib.tuneables.TuneablesManager;
import frc.lib.tuneables.extensions.TuneableArmFeedforward;
import frc.lib.tuneables.extensions.TuneableTrapezoidProfile;
import frc.robot.Robot;
import frc.robot.subsystems.wrist.io.WristIO;
import frc.robot.subsystems.wrist.io.WristIOSparkMax;
import frc.robot.utils.PrimitiveRotationalSensorHelper;
import frc.robot.subsystems.wrist.WristConstants.IsAtAngle;
import frc.robot.subsystems.wrist.io.WristIOSim;
import static frc.robot.subsystems.wrist.WristConstants.*;

public class Wrist extends SubsystemBase implements Tuneable {
    private final LogFieldsTable fieldsTable = new LogFieldsTable(getName());

    private final WristIO io = Robot.isSimulation() ? new WristIOSim(fieldsTable) : new WristIOSparkMax(fieldsTable);

    private final WristVisualizer wristVisualizer = new WristVisualizer(fieldsTable, "Real Mech2d",
            new Color8Bit(Color.kPurple));
    private final WristVisualizer desiredWristVisualizer = new WristVisualizer(fieldsTable, "Desired Visualizer",
            new Color8Bit(Color.kYellow));

    private final PrimitiveRotationalSensorHelper wristAngleDegreesHelper;

    private final TuneableTrapezoidProfile trapezoidProfile = new TuneableTrapezoidProfile(
            new TrapezoidProfile.Constraints(WRIST_MAX_VELOCITY_DEG_PER_SEC, WRIST_MAX_ACCELERATION_DEG_PER_SEC));

    private final PIDController wristPIDcontroller = new PIDController(KP, KI, KD);

    private final TuneableArmFeedforward feedForwardWrist = Robot.isSimulation()
            ? new TuneableArmFeedforward(SIM_KS, SIM_KG, SIM_KV, SIM_KA)
            : new TuneableArmFeedforward(KS, KG, KV, KA);

    public Wrist() {
        fieldsTable.update();
        wristAngleDegreesHelper = new PrimitiveRotationalSensorHelper(io.wristAngleDegrees.getAsDouble(),
                WRIST_ANGLE_OFFSET_DEGREES);
        wristAngleDegreesHelper.enableContinousWrap(UPPER_BOUND_WRAP, 360);

        TuneablesManager.add("Wrist", (Tuneable) this);
    }

    @Override
    public void periodic() {
        wristAngleDegreesHelper.update(io.wristAngleDegrees.getAsDouble());
        fieldsTable.recordOutput("Wrist Angle Degrees", getAbsoluteAngleDegrees());
        wristVisualizer.update(getAbsoluteAngleDegrees());
    }

    public void setWristVoltage(double voltage) {
        fieldsTable.recordOutput("Demand Voltage", voltage);
        voltage = MathUtil.clamp(voltage, -WRIST_VOLTAGE_LIMIT, WRIST_VOLTAGE_LIMIT);
        fieldsTable.recordOutput("Real voltage", voltage);
        io.setVoltage(voltage);
    }

    public double getAbsoluteAngleDegrees() {
        return wristAngleDegreesHelper.getAngle();
    }

    public double calculateFeedforward(double wristDesiredAngleDegrees, double wristDesiredVelocity, boolean usePID) {
        desiredWristVisualizer.update(wristDesiredAngleDegrees);
        fieldsTable.recordOutput("desired angle degrees", wristDesiredAngleDegrees);
        fieldsTable.recordOutput("Desired velocity", wristDesiredVelocity);

        double voltage = feedForwardWrist.calculate(Math.toRadians(wristDesiredAngleDegrees), wristDesiredVelocity);

        if (usePID) {
            voltage += wristPIDcontroller.calculate(getAbsoluteAngleDegrees(), wristDesiredAngleDegrees);
        }

        fieldsTable.recordOutput("feedforward result", voltage);
        return voltage;
    }

    public void resetPID() {
        wristPIDcontroller.reset();
    }

    public TrapezoidProfile.State calculateTrapezoidProfile(double time, TrapezoidProfile.State initialState,
            TrapezoidProfile.State goalState) {
        return trapezoidProfile.calculate(time, initialState, goalState);
    }

    public boolean isAtAngle(double desiredAngle) {
        return desiredAngle - getAbsoluteAngleDegrees() < IsAtAngle.MAX_WRIST_ANGLE_DEVAITION
                && desiredAngle - getAbsoluteAngleDegrees() > IsAtAngle.MIN_WRIST_ANGLE_DEVAITION;
    }

    public void stop() {
        setWristVoltage(0);
    }

    @Override
    public void initTuneable(TuneableBuilder builder) {
        builder.addChild("Wrist PID", wristPIDcontroller);

        builder.addChild("Wrist feedforward", feedForwardWrist);

        builder.addChild("Trapezoid profile", trapezoidProfile);

        builder.addChild("wrist angle degrees", wristAngleDegreesHelper);
    }
}