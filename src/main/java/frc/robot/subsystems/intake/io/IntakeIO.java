package frc.robot.subsystems.intake.io;

import frc.lib.logfields.IOBase;
import frc.lib.logfields.LogFieldsTable;

public abstract class IntakeIO extends IOBase{


    public IntakeIO(LogFieldsTable fieldsTable){
        super(fieldsTable);

    }

    protected abstract void setRollersSpeed(double rollersIntakeSpeed);
}
