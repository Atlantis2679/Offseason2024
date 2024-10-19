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

    private final SlewRateLimiter accelLimitHorizontal = new SlewRateLimiter(IntakeConstants.HORIZONTAL_ACCEL_LIMIT);
    private final SlewRateLimiter accelLimitVertical = new SlewRateLimiter(IntakeConstants.HORIZONTAL_ACCEL_LIMIT);

    public Intake() {
        fieldsTable.update();
    }

    @Override
    public void periodic() {
        fieldsTable.recordOutput("current command",
                getCurrentCommand() != null ? getCurrentCommand().getName() : "none");
    }

    public void setRollerSpeed(double horizontalSpeed, double verticalSpeed) {
        fieldsTable.recordOutput("horizontal rollers demand speed", horizontalSpeed);
        fieldsTable.recordOutput("vertical rollers demand speed", verticalSpeed);

        horizontalSpeed = MathUtil.clamp(horizontalSpeed, -1, 1);
        horizontalSpeed = accelLimitHorizontal.calculate(horizontalSpeed);
        verticalSpeed = MathUtil.clamp(verticalSpeed, -1, 1);
        verticalSpeed = accelLimitVertical.calculate(verticalSpeed);

        fieldsTable.recordOutput("horizontal rollers actual speed", horizontalSpeed);
        intakeIO.setHorizontalRollerSpeed(horizontalSpeed);
        fieldsTable.recordOutput("vertical rollers actual speed", verticalSpeed);
        intakeIO.setVerticalRollerSpeed(verticalSpeed);
    }

    public void stop() {
        fieldsTable.recordOutput("horizontal rollers demand speed", 0.0);
        fieldsTable.recordOutput("vertical rollers demand speed", 0.0);
        fieldsTable.recordOutput("horizontal rollers actual speed", 0.0);
        fieldsTable.recordOutput("vertical rollers actual speed", 0.0);

        intakeIO.setHorizontalRollerSpeed(0);
        intakeIO.setVerticalRollerSpeed(0);

    }
}
