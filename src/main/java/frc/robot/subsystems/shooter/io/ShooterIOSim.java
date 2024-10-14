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
    private LogFieldsTable fieldsTable;

    public ShooterIOSim(LogFieldsTable fieldsTable) {
        super(fieldsTable);
        this.fieldsTable = fieldsTable;
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
        fieldsTable.recordOutput("upperRollerSpeedRPMSim", getUpperRollerSpeedRPM());
        upperRollorSim.setInputVoltage(voltage);
        upperRollorSim.update(0.02);
    }

    @Override
    public void setLowerRollerVoltage(double voltage) {
        fieldsTable.recordOutput("lowerRollerSpeedRPMSim", getLowerRollerSpeedRPM());
        lowerRollorSim.setInputVoltage(voltage);
        lowerRollorSim.update(0.02);
    }

}