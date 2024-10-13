// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.wrist.io;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel;
import static frc.robot.RobotMap.*;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.lib.logfields.LogFieldsTable;
import static frc.robot.subsystems.wrist.WristConstants.*;

/** Add your docs here. */
public class WristIOSparkMax extends WristIO {
    private final CANSparkMax wristMotorLeft = new CANSparkMax(CANBUS.WRIST_LEFT_MOTOR_ID,
            CANSparkLowLevel.MotorType.kBrushless);
    private final CANSparkMax wristMotorRight = new CANSparkMax(CANBUS.WRIST_RIGHT_MOTOR_ID,
            CANSparkLowLevel.MotorType.kBrushless);
    private final DutyCycleEncoder wristEncoder = new DutyCycleEncoder(DIO.WRIST_ENCODER_ID);

    public WristIOSparkMax(LogFieldsTable fieldsTable) {
        super(fieldsTable);
        wristMotorRight.follow(wristMotorLeft);
        wristMotorLeft.setSmartCurrentLimit(CURRENT_LIMIT_AMPS);
        wristMotorLeft.setIdleMode(IdleMode.kBrake);
        wristEncoder.setDistancePerRotation(360);
    }

    @Override
    public double getWristAngleDegrees() {
        return wristEncoder.getAbsolutePosition();
    }

    @Override
    public void setVoltage(double voltage) {
        wristMotorLeft.setVoltage(voltage);
    }
}
