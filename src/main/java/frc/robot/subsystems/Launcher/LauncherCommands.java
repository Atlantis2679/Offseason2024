package frc.robot.subsystems.Launcher;

import edu.wpi.first.wpilibj2.command.Command;

import static frc.robot.subsystems.Launcher.LauncherConstants.*;

import java.util.function.DoubleSupplier;

public class LauncherCommands {
    private final Launcher launcher;

    public LauncherCommands(Launcher launcher) {
        this.launcher = launcher;
    }

    public Command load() {
        return launcher.run(() -> launcher.setSpeed(LOADING_SPEED_PRECENTAGE)).until(() -> launcher.getIsNoteInside())
                .finallyDo(() -> launcher.stop());
    }

    public Command release() {
         return launcher.run(() -> launcher.setSpeed(RELEASING_SPEED_PRECENTAGE)).until(() -> !launcher.getIsNoteInside())
                .finallyDo(() -> launcher.stop());
    }

    public Command manualController(DoubleSupplier launcherSpeed){
        return launcher.run(()-> launcher.setSpeed(launcherSpeed.getAsDouble()));
    }
}