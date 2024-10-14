// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Launcher.io;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.CANSparkLowLevel;

import frc.lib.logfields.LogFieldsTable;
import static frc.robot.RobotMap.*;

/** Add your docs here. */
public class LauncherIOSparkMax extends LauncherIO {

    private final DigitalInput beambreak = new DigitalInput(DIO.BEAM_BREAK_ID);
    private final CANSparkMax launcherMotor = new CANSparkMax(CANBUS.LAUNCHER_MOTOR_ID,
            CANSparkLowLevel.MotorType.kBrushless);

    public LauncherIOSparkMax(LogFieldsTable fieldsTable) {
        super(fieldsTable);
    }

    @Override
    public boolean getIsNoteInside() {
        return beambreak.get();
    }

    @Override
    public void setSpeed(double speed) {
        launcherMotor.set(speed);
    }

}
