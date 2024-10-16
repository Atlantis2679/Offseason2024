// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter.io;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.lib.logfields.LogFieldsTable;
import frc.robot.subsystems.shooter.ShooterConstants;

import static frc.robot.RobotMap.CANBUS;

/** Add your docs here. */
public class ShooterIOSparkMax extends ShooterIO {
    private final CANSparkMax upperRoller = new CANSparkMax(CANBUS.SHOOTER_UPPER_ROLLER_ID, MotorType.kBrushless);
    private final CANSparkMax lowerRoller = new CANSparkMax(CANBUS.SHOOTER_LOWER_ROLLER_ID, MotorType.kBrushless);

    private final RelativeEncoder upperRollerEncoder = upperRoller.getEncoder();
    private final RelativeEncoder lowerRollerEncoder = lowerRoller.getEncoder();

    public ShooterIOSparkMax(LogFieldsTable fieldsTable) {
        super(fieldsTable);
        upperRoller.setSmartCurrentLimit(ShooterConstants.ROLLERS_CURRENT_LIMIT);
        lowerRoller.setSmartCurrentLimit(ShooterConstants.ROLLERS_CURRENT_LIMIT);
    }

    @Override
    protected double getUpperRollerSpeedRPM() {
        return -upperRollerEncoder.getVelocity();
    }

    @Override
    protected double getLowerRollerSpeedRPM() {
        return lowerRollerEncoder.getVelocity();
    }

    @Override
    public void setUpperRollerVoltage(double voltage) {
        upperRoller.setVoltage(voltage);
    }

    @Override
    public void setLowerRollerVoltage(double voltage) {
        lowerRoller.setVoltage(voltage);
    }
}
