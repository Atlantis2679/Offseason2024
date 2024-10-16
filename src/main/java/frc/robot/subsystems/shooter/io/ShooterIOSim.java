package frc.robot.subsystems.shooter.io;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import frc.lib.logfields.LogFieldsTable;
import frc.robot.subsystems.shooter.ShooterConstants;

public class ShooterIOSim extends ShooterIO {
    private final FlywheelSim upperMotorSim = new FlywheelSim(ShooterConstants.LINEAR_SYSTEM, DCMotor.getNEO(1),
            ShooterConstants.GERAING);
    private final FlywheelSim lowerMotorSim = new FlywheelSim(ShooterConstants.LINEAR_SYSTEM, DCMotor.getNEO(1),
            ShooterConstants.GERAING);

    public ShooterIOSim(LogFieldsTable fieldsTable) {
        super(fieldsTable);
    }

    @Override
    protected void periodicBeforeFields() {
        upperMotorSim.update(0.02);
        lowerMotorSim.update(0.02);
    }

    @Override
    protected double getUpperRollerSpeedRPM() {
        return upperMotorSim.getAngularVelocityRPM();
    }

    @Override
    protected double getLowerRollerSpeedRPM() {
        return lowerMotorSim.getAngularVelocityRPM();
    }

    @Override
    public void setUpperRollerVoltage(double voltage) {
        upperMotorSim.setInputVoltage(voltage);
    }

    @Override
    public void setLowerRollerVoltage(double voltage) {
        lowerMotorSim.setInputVoltage(voltage);
    }

    @Override
    protected double getUpperMotorCurrent() {
        return upperMotorSim.getCurrentDrawAmps();
    }

    @Override
    protected double getLowerMotorCurrent() {
        return lowerMotorSim.getCurrentDrawAmps();
    }
}