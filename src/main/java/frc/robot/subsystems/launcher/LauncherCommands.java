package frc.robot.subsystems.launcher;

import edu.wpi.first.wpilibj2.command.Command;

import java.util.function.DoubleSupplier;

public class LauncherCommands {
    private final Launcher launcher;

    public LauncherCommands(Launcher launcher) {
        this.launcher = launcher;
    }

    public Command spin(double speed) {
        return launcher.run(() -> launcher.setSpeed(speed)).finallyDo(() -> launcher.stop()).withName("launcherLoad");
    }

    public Command manualController(DoubleSupplier launcherSpeed) {
        return launcher.run(() -> launcher.setSpeed(launcherSpeed.getAsDouble())).finallyDo(launcher::stop)
                .withName("launcherManualController");
    }
}
