// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.pivot;

import static frc.robot.subsystems.pivot.PivotConstants.*;

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
import frc.robot.subsystems.pivot.PivotConstants.IsAtAngle;
import frc.robot.subsystems.pivot.io.PivotIO;
import frc.robot.subsystems.pivot.io.PivotIOSim;
import frc.robot.subsystems.pivot.io.PivotIOSparkMax;
import frc.robot.utils.PrimitiveRotationalSensorHelper;

public class Pivot extends SubsystemBase implements Tuneable {
    private final LogFieldsTable fieldsTable = new LogFieldsTable(getName());

    private final PivotIO io = Robot.isSimulation() ? new PivotIOSim(fieldsTable) : new PivotIOSparkMax(fieldsTable);

    private final PivotVisualizer pivotVisualizer = new PivotVisualizer(fieldsTable, "Real Mech2d",
            new Color8Bit(Color.kPurple));
    private final PivotVisualizer desiredPivotVisualizer = new PivotVisualizer(fieldsTable, "Desired Visualizer",
            new Color8Bit(Color.kYellow));

    private final PrimitiveRotationalSensorHelper pivotAngleDegreesHelper;

    private final TuneableTrapezoidProfile trapezoidProfile = new TuneableTrapezoidProfile(
            new TrapezoidProfile.Constraints(PIVOT_MAX_VELOCITY_DEG_PER_SEC, PIVOT_MAX_ACCELERATION_DEG_PER_SEC));

    private final PIDController pivotPIDcontroller = new PIDController(KP, KI, KD);

    private final TuneableArmFeedforward feedForwardPivot = Robot.isSimulation()
            ? new TuneableArmFeedforward(SIM_KS, SIM_KG, SIM_KV, SIM_KA)
            : new TuneableArmFeedforward(KS, KG, KV, KA);

    public Pivot() {
        fieldsTable.update();
        pivotAngleDegreesHelper = new PrimitiveRotationalSensorHelper(io.pivotAngleDegrees.getAsDouble(),
                PIVOT_ANGLE_OFFSET_DEGREES);
        pivotAngleDegreesHelper.enableContinousWrap(UPPER_BOUND_WRAP, 360);

        TuneablesManager.add("pivot", (Tuneable) this);
    }

    @Override
    public void periodic() {
        pivotAngleDegreesHelper.update(io.pivotAngleDegrees.getAsDouble());
        fieldsTable.recordOutput("Pivot Angle Degrees", getAbsoluteAngleDegrees());
        pivotVisualizer.update(getAbsoluteAngleDegrees());
    }

    public void setPivotVoltage(double voltage) {
        fieldsTable.recordOutput("Demand Voltage", voltage);
        voltage = MathUtil.clamp(voltage, -PIVOT_VOLTAGE_LIMIT, PIVOT_VOLTAGE_LIMIT);
        fieldsTable.recordOutput("Real voltage", voltage);
        io.setVoltage(voltage);
    }

    public double getAbsoluteAngleDegrees() {
        return pivotAngleDegreesHelper.getAngle();
    }

    public double calculateFeedforward(double pivotDesiredAngleDegrees, double pivotDesiredVelocity, boolean usePID) {
        desiredPivotVisualizer.update(pivotDesiredAngleDegrees);
        fieldsTable.recordOutput("desired angle degrees", pivotDesiredAngleDegrees);
        fieldsTable.recordOutput("Desired velocity", pivotDesiredVelocity);

        double voltage = feedForwardPivot.calculate(Math.toRadians(pivotDesiredAngleDegrees), pivotDesiredVelocity);

        if (usePID) {
            voltage += pivotPIDcontroller.calculate(getAbsoluteAngleDegrees(), pivotDesiredAngleDegrees);
        }

        fieldsTable.recordOutput("feedforward result", voltage);
        return voltage;
    }

    public void resetPID() {
        pivotPIDcontroller.reset();
    }

    public TrapezoidProfile.State calculateTrapezoidProfile(double time, TrapezoidProfile.State initialState,
            TrapezoidProfile.State goalState) {
        return trapezoidProfile.calculate(time, initialState, goalState);
    }

    public boolean isAtAngle(double desiredAngle) {
        return desiredAngle - getAbsoluteAngleDegrees() < IsAtAngle.MAX_PIVOT_ANGLE_DEVAITION
                && desiredAngle - getAbsoluteAngleDegrees() > IsAtAngle.MIN_PIVOT_ANGLE_DEVAITION;
    }

    public void stop() {
        setPivotVoltage(0);
    }

    @Override
    public void initTuneable(TuneableBuilder builder) {
        builder.addChild("Pivot PID", pivotPIDcontroller);

        builder.addChild("Pivot feedforward", feedForwardPivot);

        builder.addChild("Trapezoid profile", trapezoidProfile);

        builder.addChild("pivot angle degrees", pivotAngleDegreesHelper);
    }
}