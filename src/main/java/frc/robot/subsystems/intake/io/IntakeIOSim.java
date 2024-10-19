package frc.robot.subsystems.intake.io;

import frc.lib.logfields.LogFieldsTable;

public class IntakeIOSim extends IntakeIO {
    public IntakeIOSim(LogFieldsTable fieldsTable) {
        super(fieldsTable);
    }

    @Override
    public void setHorizontalRollerSpeed(double rollersSpeed){
    }

    @Override
    public void setVerticalRollerSpeed(double rollersSpeed){
    }

    @Override
    protected double getHorizontalMotorCurrent() {
        return 0;
    }

    @Override
    protected double getLeftVerticalMotorCurrent() {
        return 0;
    }

    @Override
    protected double getRightVerticalMotorCurrent() {
        return 0;
    }
}
