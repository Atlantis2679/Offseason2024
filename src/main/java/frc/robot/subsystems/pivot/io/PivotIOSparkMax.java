// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.pivot.io;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel;
import static frc.robot.RobotMap.*;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.lib.logfields.LogFieldsTable;
import frc.robot.subsystems.pivot.PivotConstants;

/** Add your docs here. */
public class PivotIOSparkMax extends PivotIO {
    private final CANSparkMax motor = new CANSparkMax(CANBUS.PIVOT_MOTOR_ID,
            CANSparkLowLevel.MotorType.kBrushless);
    private final DutyCycleEncoder encoder = new DutyCycleEncoder(DIO.PIVOT_ENCODER_ID);

    public PivotIOSparkMax(LogFieldsTable fieldsTable) {
        super(fieldsTable);
        motor.restoreFactoryDefaults();
        motor.setSmartCurrentLimit(PivotConstants.CURRENT_LIMIT_AMPS);
        motor.setIdleMode(IdleMode.kBrake);
        motor.setInverted(true);
        encoder.setDistancePerRotation(1);

        motor.burnFlash();
    }

    @Override
    public double getPivotAngleDegrees() {
        return encoder.getAbsolutePosition() * 360;
    }

    @Override
    public void setVoltage(double voltage) {
        motor.setVoltage(voltage);
    }

    @Override
    protected double getMotorCurrent() {
        return motor.getOutputCurrent();
    }
}
