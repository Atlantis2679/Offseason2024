// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.wrist.io;

import java.util.function.DoubleSupplier;

import frc.lib.logfields.IOBase;
import frc.lib.logfields.LogFieldsTable;

/** Add your docs here. */
public abstract class WristIO extends IOBase {

    public final DoubleSupplier wristAngleDegrees = fields.addDouble("wristAngleDegrees", this::getWristAngleDegrees);

    protected WristIO(LogFieldsTable fieldsTable) {
        super(fieldsTable);
    }

    // Inputs
    protected abstract double getWristAngleDegrees();

    // Outputs
    public abstract void setSpeed(double wristSpeed);

    public abstract void setVoltage(double wristVoltage);

}
