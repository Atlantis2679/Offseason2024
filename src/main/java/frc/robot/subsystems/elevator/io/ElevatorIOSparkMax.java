
package frc.robot.subsystems.elevator.io;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.lib.logfields.LogFieldsTable;
import static frc.robot.subsystems.elevator.ElevatorConstants.*;
import static frc.robot.RobotMap.LEFT_LIMIT_SWITCH_ID;
import static frc.robot.RobotMap.RIGHT_LIMIT_SWITCH_ID;

public class ElevatorIOSparkMax extends ElevatorIO {
    private final CANSparkMax motorRight = new CANSparkMax(RIGHT_ELEVATOR_MOTOR_ID, MotorType.kBrushless);
    private final CANSparkMax motorLeft = new CANSparkMax(LEFT_ELEVATOR_MOTOR_ID, MotorType.kBrushless);
    private final DigitalInput leftLimitSwitch = new DigitalInput(LEFT_LIMIT_SWITCH_ID);
    private final DigitalInput rightLimitSwitch = new DigitalInput(RIGHT_LIMIT_SWITCH_ID);

    public ElevatorIOSparkMax(LogFieldsTable fieldsTable) {
        super(fieldsTable);
        motorRight.restoreFactoryDefaults();
        motorLeft.restoreFactoryDefaults();
        motorLeft.setIdleMode(IdleMode.kBrake);
        motorRight.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public void setSpeedRight(double speed) {
        motorRight.set(speed);
    }

    @Override
    public void setSpeedLeft(double speed) {
        motorLeft.set(speed);
    }
    
    @Override
    public boolean getLeftLimitSwitchValue() {
        return !leftLimitSwitch.get();
    }

    @Override
    protected boolean getRightLimitSwitchValue() {
        return !rightLimitSwitch.get();
    }
}
