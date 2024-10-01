// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter.io;

import java.util.function.DoubleSupplier;

import frc.lib.logfields.IOBase;
import frc.lib.logfields.LogFieldsTable;

/** Add your docs here. */
public abstract class ShooterIO extends IOBase{
    public final DoubleSupplier upperRollerSpeedRPM = fields.addDouble(null, null);
    public final DoubleSupplier lowerRollerSpeedRPM = fields.addDouble(null, null);

    public ShooterIO(LogFieldsTable fieldsTable) {
        super(fieldsTable);
    }

    // INPUTS

    protected abstract double getUpperRollerSpeedRPM();

    protected abstract double getLowerRollerSpeedRPM();
    

    // OUTPUT

    public abstract void setUpperRollerVoltage(double voltage);

    public abstract void setLowerRollerVoltage(double voltage);

}
