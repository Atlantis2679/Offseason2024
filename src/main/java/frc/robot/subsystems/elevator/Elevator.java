package frc.robot.subsystems.elevator;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.logfields.LogFieldsTable;
import frc.robot.subsystems.elevator.io.ElevatorIO;
import frc.robot.subsystems.elevator.io.ElevatorIOSparkMax;

public class Elevator extends SubsystemBase {
    private final LogFieldsTable fieldsTable = new LogFieldsTable("Elevator");
    private final ElevatorIO io = new ElevatorIOSparkMax(fieldsTable);
    
    public void setSpeedLeft(double speed){
        speed = MathUtil.clamp(speed, -ElevatorConstants.SPEED_LIMIT, ElevatorConstants.SPEED_LIMIT);
        io.setSpeedLeft(speed);
    }

    public void setSpeedRight(double speed){
        speed = MathUtil.clamp(speed, -ElevatorConstants.SPEED_LIMIT, ElevatorConstants.SPEED_LIMIT);
        io.setSpeedRight(speed);
    } 

    public void setSpeed(double speed, boolean isGoingDown){
        speed = isGoingDown ? 

    }

    public void stop(){
        io.setSpeedLeft(0);
        io.setSpeedRight(0);
    }


    
}