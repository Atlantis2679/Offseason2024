package frc.robot.subsystems.pivot.io;

import static frc.robot.subsystems.pivot.PivotConstants.*;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import frc.lib.logfields.LogFieldsTable;

public class PivotIOSim extends PivotIO {
    private final SingleJointedArmSim pivotMotor = new SingleJointedArmSim(
            DCMotor.getNEO(2),
            JOINT_GEAR_RATIO,
            PIVOT_JKG_METERS_SQUARED,
            2,
            Math.toRadians(PIVOT_TURNING_MIN_DEGREES),
            Math.toRadians(PIVOT_TURNING_MAX_DEGREES),
            true,
            0);

    @Override
    protected void periodicBeforeFields() {
        pivotMotor.update(0.02);
    }

    @Override
    protected double getPivotAngleDegrees() {
        return Math.toDegrees(pivotMotor.getAngleRads());
    }

    @Override
    public void setVoltage(double voltage) {
        pivotMotor.setInputVoltage(voltage);
    }

    public PivotIOSim(LogFieldsTable logFieldsTable) {
        super(logFieldsTable);
    }

    @Override
    protected double getMotorCurrent() {
        return pivotMotor.getCurrentDrawAmps();
    }
}
