package frc.robot.subsystems.Launcher.io;

import java.util.function.BooleanSupplier;

import frc.lib.logfields.IOBase;
import frc.lib.logfields.LogFieldsTable;

public abstract class LauncherIO extends IOBase {
    public final BooleanSupplier isNoteInside = fields.addBoolean("Is note inside launcher", this::getIsNoteInside);

    protected LauncherIO(LogFieldsTable fieldsTable) {
        super(fieldsTable);
    }

    // inputs
    public abstract boolean getIsNoteInside();

    // outputs
    public abstract void setSpeed(double speed);
}
