// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter.io;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.lib.logfields.LogFieldsTable;
import frc.robot.subsystems.shooter.ShooterConstants;

import static frc.robot.RobotMap.CANBUS;

/** Add your docs here. */
public class ShooterIOSparkMax extends ShooterIO {
    private final CANSparkMax upperMotor = new CANSparkMax(CANBUS.SHOOTER_UPPER_ROLLER_ID, MotorType.kBrushless);
    private final CANSparkMax lowerMotor = new CANSparkMax(CANBUS.SHOOTER_LOWER_ROLLER_ID, MotorType.kBrushless);

    private final RelativeEncoder upperRollerEncoder = upperMotor.getEncoder();
    private final RelativeEncoder lowerRollerEncoder = lowerMotor.getEncoder();

    public ShooterIOSparkMax(LogFieldsTable fieldsTable) {
        super(fieldsTable);
        upperMotor.restoreFactoryDefaults();
        lowerMotor.restoreFactoryDefaults();
        upperMotor.setSmartCurrentLimit(ShooterConstants.ROLLERS_CURRENT_LIMIT);
        lowerMotor.setSmartCurrentLimit(ShooterConstants.ROLLERS_CURRENT_LIMIT);
        upperMotor.setIdleMode(IdleMode.kCoast);
        lowerMotor.setIdleMode(IdleMode.kCoast);
        upperMotor.setInverted(true);
        lowerMotor.setInverted(true);

        lowerMotor.burnFlash();
        upperMotor.burnFlash();
    }

    @Override
    protected double getUpperRollerSpeedRPM() {
        return upperRollerEncoder.getVelocity();
    }

    @Override
    protected double getLowerRollerSpeedRPM() {
        return lowerRollerEncoder.getVelocity();
    }

    @Override
    public void setUpperRollerVoltage(double voltage) {
        upperMotor.setVoltage(voltage);
    }

    @Override
    public void setLowerRollerVoltage(double voltage) {
        lowerMotor.setVoltage(voltage);
    }

    @Override
    protected double getUpperMotorCurrent() {
        return upperMotor.getOutputCurrent();
    }

    @Override
    protected double getLowerMotorCurrent() {
        return lowerMotor.getOutputCurrent();
    }
}
