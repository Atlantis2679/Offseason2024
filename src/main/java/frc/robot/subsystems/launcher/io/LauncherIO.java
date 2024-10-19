package frc.robot.subsystems.launcher.io;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import frc.lib.logfields.IOBase;
import frc.lib.logfields.LogFieldsTable;

public abstract class LauncherIO extends IOBase {
    public final BooleanSupplier isNoteInside = fields.addBoolean("Is note inside launcher", this::getIsNoteInside);
    public final DoubleSupplier motorCurrent = fields.addDouble("motorCurrent", this::getMotorCurrent);

    protected LauncherIO(LogFieldsTable fieldsTable) {
        super(fieldsTable);
    }

    // inputs
    public abstract boolean getIsNoteInside();

    public abstract double getMotorCurrent();

    // outputs
    public abstract void setSpeed(double speed);
}
