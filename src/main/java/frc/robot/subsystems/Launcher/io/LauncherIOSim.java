// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Launcher.io;

import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import frc.lib.logfields.LogFieldsTable;

/** Add your docs here. */
public class LauncherIOSim extends LauncherIO{
    private final FlywheelSim launcherMotorSim = new FlywheelSim(null, 0, 0);
    public LauncherIOSim(LogFieldsTable fieldsTable) {
        super(fieldsTable);
    }

    @Override
    public boolean getIsNoteInside() {
        return false;
    }

    @Override
    public void setSpeed(double speed) {
        launcherMotorSim.setInputVoltage(speed);
    }}
