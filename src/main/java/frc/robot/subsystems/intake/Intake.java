// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.logfields.LogFieldsTable;
import frc.robot.Robot;
import frc.robot.subsystems.intake.io.IntakeIO;
import frc.robot.subsystems.intake.io.IntakeIOSim;
import frc.robot.subsystems.intake.io.IntakeIOSparkMax;

public class Intake extends SubsystemBase {
    private final LogFieldsTable fieldsTable = new LogFieldsTable(getName());
    private final IntakeIO intakeIO = Robot.isSimulation()
            ? new IntakeIOSim(fieldsTable)
            : new IntakeIOSparkMax(fieldsTable);
    private boolean shouldStop = false;

    private final SlewRateLimiter speedLimiter = new SlewRateLimiter(IntakeConstants.INTAKE_SPEED_RATE_LIMIT);

    public Intake() {
        fieldsTable.update();
    }

    @Override
    public void periodic() {
        if (shouldStop) {
            setRollerSpeed(0, 0);
        }
    }

    public void softStop() {
        shouldStop = true;
    }

    public void setRollerSpeed(double horizontalSpeed, double verticalSpeed) {
        shouldStop = false;

        fieldsTable.recordOutput("horizontal rollers demand speed", horizontalSpeed);
        fieldsTable.recordOutput("vertical rollers demand speed", verticalSpeed);

        horizontalSpeed = MathUtil.clamp(horizontalSpeed, 1, -1);
        horizontalSpeed = speedLimiter.calculate(horizontalSpeed);
        verticalSpeed = MathUtil.clamp(verticalSpeed, 1, -1);
        verticalSpeed = speedLimiter.calculate(verticalSpeed);

        fieldsTable.recordOutput("horizontal rollers actual speed", horizontalSpeed);
        intakeIO.setHorizontalRollerSpeed(horizontalSpeed);
        fieldsTable.recordOutput("vertical rollers actual speed", verticalSpeed);
        intakeIO.setHorizontalRollerSpeed(verticalSpeed);
    }

    public void stop() {
        shouldStop = false;

        fieldsTable.recordOutput("horizontal rollers demand speed", 0.0);
        fieldsTable.recordOutput("vertical rollers demand speed", 0.0);
        fieldsTable.recordOutput("horizontal rollers actual speed", 0.0);
        fieldsTable.recordOutput("vertical rollers actual speed", 0.0);

        intakeIO.setHorizontalRollerSpeed(0);
        intakeIO.setVerticalRollerSpeed(0);

    }
}
