package frc.robot.subsystems.wrist.io;

import static frc.robot.subsystems.wrist.WristConstants.*;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import frc.lib.logfields.LogFieldsTable;

public class WristIOSim extends WristIO {
    private final SingleJointedArmSim wristMotor = new SingleJointedArmSim(
            DCMotor.getNEO(2),
            JOINT_GEAR_RATIO,
            WRIST_JKG_METERS_SQUARED,
            1,
            Math.toRadians(WRIST_TURNING_MIN_DEGREES),
            Math.toRadians(WRIST_TURNING_MAX_DEGREES),
            false,
            0);

    @Override
    protected void periodicBeforeFields() {
        wristMotor.update(0.02);
    }

    @Override
    protected double getWristAngleDegrees() {
        return Math.toDegrees(wristMotor.getAngleRads());
    }

    @Override
    public void setVoltage(double voltage) {
        wristMotor.setInputVoltage(voltage);
    }

    public WristIOSim(LogFieldsTable logFieldsTable) {
        super(logFieldsTable);
    }

    @Override
    public void setSpeed(double speed) {
        wristMotor.setInputVoltage(speed / 12);
    }
}
