// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;
import edu.wpi.first.wpilibj2.command.Command;

public class IntakeCommands {
  private final Intake intake;

  public IntakeCommands(Intake intake){
    this.intake = intake;
  }

  public Command takeIn(){//collect

    return intake.runEnd(() -> intake.setRollerSpeed(IntakeConstants.INTAKE_ROLLER_SPEED), intake::stop);
  }
  

  public Command takeInVoltage(){

    return intake.runEnd(() -> intake.setRollerSpeedVoltage(IntakeConstants.INTAKE_ROLLER_SPEED_VOLTAGE), intake::stop);
  }

}
