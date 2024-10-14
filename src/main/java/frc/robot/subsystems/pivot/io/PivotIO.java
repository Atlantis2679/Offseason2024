// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.pivot.io;

import java.util.function.DoubleSupplier;

import frc.lib.logfields.IOBase;
import frc.lib.logfields.LogFieldsTable;

/** Add your docs here. */
public abstract class PivotIO extends IOBase {
    public final DoubleSupplier pivotAngleDegrees = fields.addDouble("pivotAngleDegrees", this::getPivotAngleDegrees);

    protected PivotIO(LogFieldsTable fieldsTable) {
        super(fieldsTable);
    }

    // Inputs
    protected abstract double getPivotAngleDegrees();

    // Outputs
    public abstract void setVoltage(double voltage);
}
