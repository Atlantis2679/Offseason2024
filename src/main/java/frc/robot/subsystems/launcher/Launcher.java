package frc.robot.subsystems.launcher;

import static frc.robot.subsystems.launcher.LauncherConstants.DEBOUNCE_SEC;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
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

    private final Debouncer isNoteInsideDebouner = new Debouncer(DEBOUNCE_SEC, DebounceType.kBoth);
    private boolean isNoteInside = false;

    public Launcher() {
        fieldsTable.update();
    }

    @Override
    public void periodic() {
        fieldsTable.recordOutput("current command",
                getCurrentCommand() != null ? getCurrentCommand().getName() : "none");
        fieldsTable.recordOutput("is Node Inside", getIsNoteInside());

        isNoteInside = isNoteInsideDebouner.calculate(io.getIsNoteInside());
    }

    public boolean getIsNoteInside() {
        return isNoteInside;
    }

    public void setSpeed(double speed) {
        fieldsTable.recordOutput("demand speed", speed);
        io.setSpeed(speed);
    }

    public void stop() {
        io.setSpeed(0);
    }

}
