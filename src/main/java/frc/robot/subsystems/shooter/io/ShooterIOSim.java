package frc.robot.subsystems.shooter.io;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import frc.lib.logfields.LogFieldsTable;
import frc.robot.subsystems.shooter.ShooterConstants;

public class ShooterIOSim extends ShooterIO {
    private final FlywheelSim upperRollorSim = new FlywheelSim(ShooterConstants.LINEAR_SYSTEM, DCMotor.getNEO(1),
            ShooterConstants.GERAING);
    private final FlywheelSim lowerRollorSim = new FlywheelSim(ShooterConstants.LINEAR_SYSTEM, DCMotor.getNEO(1),
            ShooterConstants.GERAING);

    public ShooterIOSim(LogFieldsTable fieldsTable) {
        super(fieldsTable);
    }

    @Override
    protected double getUpperRollerSpeedRPM() {
        return upperRollorSim.getAngularVelocityRPM();
    }

    @Override
    protected double getLowerRollerSpeedRPM() {
        return lowerRollorSim.getAngularVelocityRPM();
    }

    @Override
    public void setUpperRollerVoltage(double voltage) {
        upperRollorSim.setInputVoltage(voltage);
    }

    @Override
    public void setLowerRollerVoltage(double voltage) {
        upperRollorSim.setInputVoltage(voltage);
    }

}
