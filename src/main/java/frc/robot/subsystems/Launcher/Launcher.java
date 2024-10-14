package frc.robot.subsystems.Launcher;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.logfields.LogFieldsTable;
import frc.robot.Robot;
import frc.robot.subsystems.Launcher.io.LauncherIO;
import frc.robot.subsystems.Launcher.io.LauncherIOSim;
import frc.robot.subsystems.Launcher.io.LauncherIOSparkMax;

public class Launcher extends SubsystemBase {
    private final LogFieldsTable fieldsTable = new LogFieldsTable(getName());
    private final LauncherIO io = Robot.isSimulation() ? new LauncherIOSparkMax(fieldsTable)
            : new LauncherIOSim(fieldsTable);

    public Launcher() {
        fieldsTable.update();
    }

    @Override
    public void periodic() {
        fieldsTable.recordOutput("is node in launcher", getIsNoteInside());
    }

    public boolean getIsNoteInside() {
        return io.getIsNoteInside();
    }

    public void setSpeed(double speed) {
        io.setSpeed(speed);
    }

    public void stop() {
        io.setSpeed(0);
    }

}
