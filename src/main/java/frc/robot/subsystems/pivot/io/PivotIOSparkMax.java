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
    private final CANSparkMax pivotMotorLeftLead = new CANSparkMax(CANBUS.PIVOT_LEFT_MOTOR_ID,
            CANSparkLowLevel.MotorType.kBrushless);
    private final CANSparkMax pivotMotorRight = new CANSparkMax(CANBUS.PIVOT_RIGHT_MOTOR_ID,
            CANSparkLowLevel.MotorType.kBrushless);
    private final DutyCycleEncoder pivotEncoder = new DutyCycleEncoder(DIO.PIVOT_ENCODER_ID);

    public PivotIOSparkMax(LogFieldsTable fieldsTable) {
        super(fieldsTable);
        pivotMotorRight.follow(pivotMotorLeftLead);
        pivotMotorLeftLead.setSmartCurrentLimit(PivotConstants.CURRENT_LIMIT_AMPS);
        pivotMotorLeftLead.setIdleMode(IdleMode.kBrake);
        pivotEncoder.setDistancePerRotation(360);
    }

    @Override
    public double getPivotAngleDegrees() {
        return pivotEncoder.getAbsolutePosition();
    }

    @Override
    public void setVoltage(double voltage) {
        pivotMotorLeftLead.setVoltage(voltage);
    }
}
