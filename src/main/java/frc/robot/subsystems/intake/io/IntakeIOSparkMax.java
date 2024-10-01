package frc.robot.subsystems.intake.io;

import frc.lib.logfields.LogFieldsTable;
import static frc.robot.RobotMap.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class IntakeIOSparkMax extends IntakeIO {
    private final CANSparkMax rightVerticalRollerMotor = new CANSparkMax(Canbus.RIGHT_VERTICAL_ROLLER_ID, MotorType.kBrushless);
    private final CANSparkMax leftVerticalRollerMotor = new CANSparkMax(Canbus.LEFT_VERTICAL_ROLLER_ID, MotorType.kBrushless);
    private final CANSparkMax horizontalRollerMotor = new CANSparkMax(Canbus.HORIZONTAL_ROLLER_ID, MotorType.kBrushless);


    public IntakeIOSparkMax(LogFieldsTable fieldsTable) {
        super(fieldsTable);
        leftVerticalRollerMotor.follow(rightVerticalRollerMotor, true);
    }
    
    @Override
    public void setRollerSpeed(double rollersIntakeSpeed){
        horizontalRollerMotor.set(rollersIntakeSpeed);
        rightVerticalRollerMotor.set(rollersIntakeSpeed);
    }

        @Override
    public void setRollerSpeedVoltage(double rollersIntakeSpeedVoltage){
        horizontalRollerMotor.setVoltage(rollersIntakeSpeedVoltage);
        rightVerticalRollerMotor.setVoltage(rollersIntakeSpeedVoltage);
    }

    public void stop(){
        horizontalRollerMotor.stopMotor();
        rightVerticalRollerMotor.stopMotor();
    }

}
