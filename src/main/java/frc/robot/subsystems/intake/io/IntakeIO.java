package frc.robot.subsystems.intake.io;

import java.util.function.DoubleSupplier;

import frc.lib.logfields.IOBase;
import frc.lib.logfields.LogFieldsTable;

public abstract class IntakeIO extends IOBase{
    public DoubleSupplier horizonalMotorCurrent = fields.addDouble("horizonalMotorCurrent", this::getHorizontalMotorCurrent);
    public DoubleSupplier leftVerticalMotorCurrent = fields.addDouble("leftVerticalMotorCurrent", this::getLeftVerticalMotorCurrent);
    public DoubleSupplier rightVerticalMotorCurrent = fields.addDouble("rightVerticalMotorCurrent", this::getRightVerticalMotorCurrent);

    public IntakeIO(LogFieldsTable fieldsTable){
        super(fieldsTable);
    }

    // INPUTS
    protected abstract double getHorizontalMotorCurrent();

    protected abstract double getLeftVerticalMotorCurrent();

    protected abstract double getRightVerticalMotorCurrent();
    
    // OUTPUTS

    public abstract void setHorizontalRollerSpeed(double rollersSpeed);

    public abstract void setVerticalRollerSpeed(double rollersSpeed);
}
