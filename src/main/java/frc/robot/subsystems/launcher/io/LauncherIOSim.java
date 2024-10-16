package frc.robot.subsystems.launcher.io;

import frc.lib.logfields.LogFieldsTable;

public class LauncherIOSim extends LauncherIO {
    public LauncherIOSim(LogFieldsTable fieldsTable) {
        super(fieldsTable);
    }

    @Override
    public boolean getIsNoteInside() {
        return false;
    }

    @Override
    public void setSpeed(double speed) {
    }

    @Override
    public double getMotorCurrent() {
        return 0;
    }
}
