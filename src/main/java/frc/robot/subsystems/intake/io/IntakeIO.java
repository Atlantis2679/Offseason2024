package frc.robot.subsystems.intake.io;

import frc.lib.logfields.IOBase;
import frc.lib.logfields.LogFieldsTable;

public abstract class IntakeIO extends IOBase{
    public IntakeIO(LogFieldsTable fieldsTable){
        super(fieldsTable);
    }

    public abstract void setHorizontalRollerSpeed(double rollersSpeed);

    public abstract void setVerticalRollerSpeed(double rollersSpeed);
}
