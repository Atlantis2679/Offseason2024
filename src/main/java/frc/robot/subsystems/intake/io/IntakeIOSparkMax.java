package frc.robot.subsystems.intake.io;

import frc.lib.logfields.LogFieldsTable;
import static frc.robot.RobotMap.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;

public class IntakeIOSparkMax extends IntakeIO {
    private final CANSparkMax rightVerticalRollerMotor = new CANSparkMax(CANBUS.RIGHT_VERTICAL_ROLLER_ID, MotorType.kBrushless);
    private final CANSparkMax leftVerticalRollerMotor = new CANSparkMax(CANBUS.LEFT_VERTICAL_ROLLER_ID, MotorType.kBrushless);
    private final CANSparkMax HorizontalRollerMotor = new CANSparkMax(CANBUS.HORIZONTAL_ROLLER_ID, MotorType.kBrushless);


    public IntakeIOSparkMax(LogFieldsTable fieldsTable) {
        super(fieldsTable);
        leftVerticalRollerMotor.follow(rightVerticalRollerMotor, true);
    }
    
    @Override
    public void setHorizontalRollersSpeed(){
        HorizontalRollerMotor.set(1);
    }

    @Override
    public void setVerticalRollersSpeed(){
        rightVerticalRollerMotor.set(1);
    }


    @Override
    public double getHorizontalRollersSpeed(){
        return 1.0;
    }

    @Override
    public void getVerticalRollersSpeed(){
    }
}
