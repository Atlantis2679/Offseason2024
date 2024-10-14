package frc.robot.subsystems.intake.io;

import frc.lib.logfields.LogFieldsTable;
import frc.robot.subsystems.intake.IntakeConstants;

import static frc.robot.RobotMap.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;

public class IntakeIOSparkMax extends IntakeIO {
    private final CANSparkMax rightVerticalRollerMotor = new CANSparkMax(Canbus.INTAKE_RIGHT_VERTICAL_ROLLER_ID,
            MotorType.kBrushless);
    private final CANSparkMax leftVerticalRollerMotor = new CANSparkMax(Canbus.INTAKE_LEFT_VERTICAL_ROLLER_ID,
            MotorType.kBrushless);
    private final CANSparkMax horizontalRollerMotor = new CANSparkMax(Canbus.INTAKE_HORIZONTAL_ROLLER_ID,
            MotorType.kBrushless);
    private final SlewRateLimiter speedLimiter = new SlewRateLimiter(IntakeConstants.INTAKE_SPEED_RATE_LIMIT);

    public IntakeIOSparkMax(LogFieldsTable fieldsTable) {
        super(fieldsTable);
        leftVerticalRollerMotor.follow(rightVerticalRollerMotor, true);
    }

    @Override
    public void setRollerSpeed(double rollersSpeed) {
        MathUtil.clamp(rollersSpeed, 1, -1);
        rollersSpeed = speedLimiter.calculate(rollersSpeed);

        horizontalRollerMotor.set(rollersSpeed);
        rightVerticalRollerMotor.set(rollersSpeed);
    }

}
