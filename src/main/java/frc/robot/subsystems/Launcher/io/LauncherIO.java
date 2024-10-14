// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Launcher.io;

import java.util.function.BooleanSupplier;

import frc.lib.logfields.IOBase;
import frc.lib.logfields.LogFieldsTable;

/** Add your docs here. */
public abstract class LauncherIO extends IOBase{
    public final BooleanSupplier isNoteInside = fields.addBoolean("Is note inside launcher", this::getIsNoteInside);

    protected LauncherIO(LogFieldsTable fieldsTable) {
        super(fieldsTable);
    }

    //inputs
    public abstract boolean getIsNoteInside();
    //outputs
    public abstract void setSpeed(double speed);
}
