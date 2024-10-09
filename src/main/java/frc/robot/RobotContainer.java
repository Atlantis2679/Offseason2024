package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.wrist.Wrist;
import frc.robot.subsystems.wrist.WristCommands;
import frc.robot.utils.NaturalXboxController;

public class RobotContainer {
    private final NaturalXboxController OperatorController = new NaturalXboxController(frc.robot.RobotMap.Wrist.controllerPort);
    public RobotContainer() {
        Wrist wrist = new Wrist();
        WristCommands wristCNDS = new WristCommands(wrist);
        configureBindings();
        wrist.setDefaultCommand(wristCNDS.MoveToAngle(OperatorController.getLeftX()*100));
    }

    private void configureBindings() {
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
