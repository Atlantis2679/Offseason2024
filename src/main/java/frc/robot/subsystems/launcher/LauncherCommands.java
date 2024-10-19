package frc.robot.subsystems.launcher;

import edu.wpi.first.wpilibj2.command.Command;

import static frc.robot.subsystems.launcher.LauncherConstants.*;

import java.util.function.DoubleSupplier;

public class LauncherCommands {
    private final Launcher launcher;

    public LauncherCommands(Launcher launcher) {
        this.launcher = launcher;
    }

    public Command load() {
        return launcher.run(() -> launcher.setSpeed(LOADING_SPEED_PRECENTAGE)).until(() -> launcher.getIsNoteInside())
                .finallyDo(() -> launcher.stop()).withName("launcherLoad");
    }

    public Command release() {
         return launcher.run(() -> launcher.setSpeed(RELEASING_SPEED_PRECENTAGE)).until(() -> !launcher.getIsNoteInside())
                .finallyDo(() -> launcher.stop()).withName("launcherRelease");
    }

    public Command manualController(DoubleSupplier launcherSpeed){
        return launcher.run(()-> launcher.setSpeed(launcherSpeed.getAsDouble())).finallyDo(launcher::stop).withName("launcherManualController");
    }
}
