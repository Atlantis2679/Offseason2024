// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj2.command.Command;

public class ElevatorCommands extends Command {
  private final Elevator elevator;

  /** Creates a new ElevatorCommands. */
  public ElevatorCommands(Elevator elevator) {
    this.elevator = elevator;
    this.addRequirements(elevator);
  }

  public Command initiateElevator(double speed, boolean isGoingDown){
    return elevator.runEnd(() -> elevator.setSpeed(speed, isGoingDown), elevator::stop);
  }

}
