// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.wrist.io;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel;
import static frc.robot.RobotMap.Wrist.*;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.lib.logfields.LogFieldsTable;

/** Add your docs here. */
public class WristIOSparkMax extends WristIO {
    private final CANSparkMax wristMotorLeft = new CANSparkMax(WRIST_LEFT_MOTOR_ID, CANSparkLowLevel.MotorType.kBrushless);
    private final CANSparkMax wristMotorRight = new CANSparkMax(WRIST_RIGHT_MOTOR_ID, CANSparkLowLevel.MotorType.kBrushless);
    private final DutyCycleEncoder wristEncoder = new DutyCycleEncoder(0                                                                                                                                                                                                                                                     );

    public WristIOSparkMax(LogFieldsTable fieldsTable) {
        super(fieldsTable);
        wristMotorRight.follow(wristMotorLeft);
        wristMotorLeft.setSmartCurrentLimit(25);
        wristMotorLeft.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public double getWristAngleDegrees() {
        return (wristEncoder.getAbsolutePosition()) * 360;
    }

    @Override
    public void setSpeed(double speed) {
        wristMotorLeft.set(speed);
    }

    @Override
    public void setVoltage(double voltage) {
        wristMotorLeft.setVoltage(voltage);
    }
}
