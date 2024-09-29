package frc.robot.subsystems.elevator.io;

import static frc.robot.RobotMap.LEFT_ELEVATOR_MOTOR_ID;
import static frc.robot.RobotMap.RIGHT_ELEVATOR_MOTOR_ID;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.lib.logfields.LogFieldsTable;

public class ElevatorIOSparkMax extends ElevatorIO{
    private final CANSparkMax rightMotor = new CANSparkMax(RIGHT_ELEVATOR_MOTOR_ID, MotorType.kBrushless);
    private final CANSparkMax leftMotor = new CANSparkMax(LEFT_ELEVATOR_MOTOR_ID, MotorType.kBrushless);

    public ElevatorIOSparkMax(LogFieldsTable fieldsTable) {
        super(fieldsTable);
        rightMotor.restoreFactoryDefaults();
        leftMotor.restoreFactoryDefaults();
        rightMotor.setSmartCurrentLimit(RIGHT_ELEVATOR_MOTOR_ID);
        leftMotor.setSmartCurrentLimit(LEFT_ELEVATOR_MOTOR_ID);
    }
    
}
