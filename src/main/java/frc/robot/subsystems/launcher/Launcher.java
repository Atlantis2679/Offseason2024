package frc.robot.subsystems.launcher;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.logfields.LogFieldsTable;
import frc.robot.Robot;
import frc.robot.subsystems.launcher.io.LauncherIO;
import frc.robot.subsystems.launcher.io.LauncherIOSim;
import frc.robot.subsystems.launcher.io.LauncherIOSparkMax;

public class Launcher extends SubsystemBase {
    private final LogFieldsTable fieldsTable = new LogFieldsTable(getName());
    private final LauncherIO io = Robot.isSimulation() ? new LauncherIOSim(fieldsTable)
            : new LauncherIOSparkMax(fieldsTable);

    public Launcher() {
        fieldsTable.update();
    }

    @Override
    public void periodic() {
        fieldsTable.recordOutput("current command",
                getCurrentCommand() != null ? getCurrentCommand().getName() : "none");
        fieldsTable.recordOutput("isNodeIn", getIsNoteInside());
    }

    public boolean getIsNoteInside() {
        return io.getIsNoteInside();
    }

    public void setSpeed(double speed) {
        fieldsTable.recordOutput("demand speed", speed);
        io.setSpeed(speed);
    }

    public void stop() {
        io.setSpeed(0);
    }

}
