// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter.io;

import java.util.function.DoubleSupplier;

import frc.lib.logfields.IOBase;
import frc.lib.logfields.LogFieldsTable;

/** Add your docs here. */
public abstract class ShooterIO extends IOBase {
    public final DoubleSupplier upperRollerSpeedRPM = fields.addDouble("upperRollerSpeedRPM",
            () -> getUpperRollerSpeedRPM());
    public final DoubleSupplier lowerRollerSpeedRPM = fields.addDouble("lowerRollerSpeedRPM",
            () -> getLowerRollerSpeedRPM());

    public final DoubleSupplier upperMotorCurrent = fields.addDouble("upperMotorCurrent",
            () -> getUpperMotorCurrent());
    public final DoubleSupplier lowerMotorCurrent = fields.addDouble("lowerMotorCurrent",
            () -> getLowerMotorCurrent());

    public ShooterIO(LogFieldsTable fieldsTable) {
        super(fieldsTable);
    }

    // INPUTS

    protected abstract double getUpperRollerSpeedRPM();

    protected abstract double getLowerRollerSpeedRPM();

    protected abstract double getUpperMotorCurrent();

    protected abstract double getLowerMotorCurrent();

    // OUTPUT

    public abstract void setUpperRollerVoltage(double voltage);

    public abstract void setLowerRollerVoltage(double voltage);

}
