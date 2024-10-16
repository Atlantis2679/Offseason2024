// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;

public class IntakeCommands {
    private final Intake intake;

    public IntakeCommands(Intake intake) {
        this.intake = intake;
    }

    public Command collect() {
        return intake.runEnd(() -> intake.setRollerSpeed(IntakeConstants.COLLECT_HORIZONTAL_ROLLER_SPEED,
                IntakeConstants.COLLECT_VERTICAL_ROLLER_SPEED), intake::stop).withName("intakeCollect");
    }

    public Command manualController(DoubleSupplier horizontalSpeed, DoubleSupplier verticalSpeed) {
        return intake.runEnd(
                () -> intake.setRollerSpeed(horizontalSpeed.getAsDouble(), verticalSpeed.getAsDouble()),
                intake::stop).withName("intakeManualController");
    }
}
