package frc.robot.subsystems.intake.io;

import frc.lib.logfields.LogFieldsTable;
import frc.robot.subsystems.intake.IntakeConstants;

import static frc.robot.RobotMap.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
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
        leftVerticalRollerMotor.restoreFactoryDefaults();
        rightVerticalRollerMotor.restoreFactoryDefaults();
        horizontalRollerMotor.restoreFactoryDefaults();

        leftVerticalRollerMotor.follow(rightVerticalRollerMotor, true);

        horizontalRollerMotor.setSmartCurrentLimit(IntakeConstants.HORIZONTAL_CURRENT_LIMIT);
        rightVerticalRollerMotor.setSmartCurrentLimit(IntakeConstants.VERTICAL_CURRENT_LIMIT);
        leftVerticalRollerMotor.setSmartCurrentLimit(IntakeConstants.VERTICAL_CURRENT_LIMIT);
        
        horizontalRollerMotor.setIdleMode(IdleMode.kCoast);
        leftVerticalRollerMotor.setIdleMode(IdleMode.kCoast);
        rightVerticalRollerMotor.setIdleMode(IdleMode.kCoast);

        horizontalRollerMotor.burnFlash();
        leftVerticalRollerMotor.burnFlash();
        rightVerticalRollerMotor.burnFlash();
    }

    @Override
    public void setHorizontalRollerSpeed(double rollersSpeed) {
        horizontalRollerMotor.set(rollersSpeed);
    }

    @Override
    public void setVerticalRollerSpeed(double rollersSpeed) {
        rightVerticalRollerMotor.set(rollersSpeed);
    }

    @Override
    protected double getHorizontalMotorCurrent() {
        return horizontalRollerMotor.getOutputCurrent();
    }

    @Override
    protected double getLeftVerticalMotorCurrent() {
        return leftVerticalRollerMotor.getOutputCurrent();
    }

    @Override
    protected double getRightVerticalMotorCurrent() {
        return rightVerticalRollerMotor.getOutputCurrent();
    }
}
