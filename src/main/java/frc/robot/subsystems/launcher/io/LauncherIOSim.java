package frc.robot.subsystems.launcher.io;

import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import frc.lib.logfields.LogFieldsTable;

public class LauncherIOSim extends LauncherIO {
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
    }
}
