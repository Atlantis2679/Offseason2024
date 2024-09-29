package frc.robot.subsystems.wrist.io;

import static frc.robot.subsystems.wrist.WristConstants.*;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import frc.lib.logfields.LogFieldsTable;

public class WristIOSim extends WristIO {
    private final SingleJointedArmSim wristMotor = new SingleJointedArmSim(
            DCMotor.getNEO(2),
            JOINT_GEAR_RATIO,
            WRIST_JKG_METERS_SQUARED,
            0,
            Math.toRadians(WRIST_TURNING_MIN_DEGREES),
            Math.toRadians(WRIST_TURNING_MAX_DEGREES),
            true,
            0);

    @Override
    protected double getWristAngleDegrees() {
        return Math.toDegrees(wristMotor.getAngleRads());
    }

    @Override
    public void setVoltage(double voltage) {
        Logger.recordOutput("set speed", voltage);
        wristMotor.setInputVoltage(voltage);
    }

    public WristIOSim(LogFieldsTable logFieldsTable) {
        super(logFieldsTable);
    }

    @Override
    public void setVoltage(double wristVoltage) {
        Logger.recordOutput("set voltage", wristVoltage);
        wristMotor.setInputVoltage(wristVoltage);
    }
}
