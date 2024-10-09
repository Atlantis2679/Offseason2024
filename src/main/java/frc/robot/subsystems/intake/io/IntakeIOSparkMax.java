package frc.robot.subsystems.intake.io;

import frc.lib.logfields.LogFieldsTable;
import frc.robot.RobotMap.Canbus;

import static frc.robot.RobotMap.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class IntakeIOSparkMax extends IntakeIO {
    private final CANSparkMax rightVerticalRollerMotor = new CANSparkMax(Canbus.INTAKE_RIGHT_VERTICAL_ROLLER_ID, MotorType.kBrushless);
    private final CANSparkMax leftVerticalRollerMotor = new CANSparkMax(Canbus.INTAKE_LEFT_VERTICAL_ROLLER_ID, MotorType.kBrushless);
    private final CANSparkMax horizontalRollerMotor = new CANSparkMax(Canbus.INTAKE_HORIZONTAL_ROLLER_ID, MotorType.kBrushless);


    public IntakeIOSparkMax(LogFieldsTable fieldsTable) {
        super(fieldsTable);
        leftVerticalRollerMotor.follow(rightVerticalRollerMotor, true);
    }
    
    @Override
    public void setRollerSpeed(double rollersSpeed){
        horizontalRollerMotor.set(rollersSpeed);
        rightVerticalRollerMotor.set(rollersSpeed);
    }

        @Override
    public void setRollerSpeedVoltage(double rollersSpeedVoltage){
        horizontalRollerMotor.setVoltage(rollersSpeedVoltage);
        rightVerticalRollerMotor.setVoltage(rollersSpeedVoltage);
    }

    public void stop(){
        horizontalRollerMotor.stopMotor();
        rightVerticalRollerMotor.stopMotor();
    }

}
