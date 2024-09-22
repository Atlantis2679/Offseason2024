package frc.robot;

import frc.lib.logfields.IOBase;
import frc.lib.logfields.LogFieldsTable;

public abstract class intakeIO extends IOBase{


    public intakeIO(LogFieldsTable fieldsTable){
        super(fieldsTable);

    }

    
    protected abstract void setHorizontalRollersSpeed();

    protected abstract void setVerticalRollersSpeed();

}
