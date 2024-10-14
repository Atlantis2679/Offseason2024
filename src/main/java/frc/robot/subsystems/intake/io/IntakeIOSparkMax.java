package frc.robot.subsystems.intake.io;

import frc.lib.logfields.LogFieldsTable;

import static frc.robot.RobotMap.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class IntakeIOSparkMax extends IntakeIO {
    private final CANSparkMax rightVerticalRollerMotor = new CANSparkMax(CANBUS.INTAKE_RIGHT_VERTICAL_ROLLER_ID,
            MotorType.kBrushless);
    private final CANSparkMax leftVerticalRollerMotor = new CANSparkMax(CANBUS.INTAKE_LEFT_VERTICAL_ROLLER_ID,
            MotorType.kBrushless);
    private final CANSparkMax horizontalRollerMotor = new CANSparkMax(CANBUS.INTAKE_HORIZONTAL_ROLLER_ID,
            MotorType.kBrushless);

    public IntakeIOSparkMax(LogFieldsTable fieldsTable) {
        super(fieldsTable);
        leftVerticalRollerMotor.follow(rightVerticalRollerMotor, true);
    }

    @Override
    public void setHorizontalRollerSpeed(double rollersSpeed) {
        horizontalRollerMotor.set(rollersSpeed);
    }

    @Override
    public void setVerticalRollerSpeed(double rollersSpeed) {
        rightVerticalRollerMotor.set(rollersSpeed);
    }
}
