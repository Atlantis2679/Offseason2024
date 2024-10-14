package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.ShooterCommands;

public class RobotContainer {
    private final CommandXboxController controller = new CommandXboxController(0);
    private final Shooter shooter = new Shooter();
    private final ShooterCommands commands = new ShooterCommands(shooter);

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        controller.a().onTrue(
                commands.reachSpeed(() -> (controller.getRightY() * 5676),
                        () -> (controller.getLeftY() * 5676)));
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
