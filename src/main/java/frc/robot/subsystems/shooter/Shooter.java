// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.logfields.LogFieldsTable;
import frc.lib.tuneables.Tuneable;
import frc.lib.tuneables.TuneableBuilder;
import frc.lib.tuneables.TuneablesManager;
import frc.lib.tuneables.extensions.TuneableSimpleMotorFeedforward;
import frc.robot.Robot;
import frc.robot.subsystems.shooter.io.ShooterIO;
import frc.robot.subsystems.shooter.io.ShooterIOSim;
import frc.robot.subsystems.shooter.io.ShooterIOSparkMax;
import static frc.robot.subsystems.shooter.ShooterConstants.*;

public class Shooter extends SubsystemBase implements Tuneable {
    private final LogFieldsTable fieldsTable = new LogFieldsTable("Shooter");
    private ShooterIO io = !Robot.isSimulation() ? new ShooterIOSparkMax(fieldsTable) : new ShooterIOSim(fieldsTable);

    private PIDController pidUpper = Robot.isSimulation() ? new PIDController(kPSim, kISim, kDSim)
            : new PIDController(kP, kI, kD);
    private PIDController pidLower = Robot.isSimulation() ? new PIDController(kPSim, kISim, kDSim)
            : new PIDController(kP, kI, kD);

    private TuneableSimpleMotorFeedforward upperFeedforward = Robot.isSimulation()
            ? new TuneableSimpleMotorFeedforward(kSSim, kVSim, kASim)
            : new TuneableSimpleMotorFeedforward(kS, kV, kA);
    private TuneableSimpleMotorFeedforward lowerFeedforward = Robot.isSimulation()
            ? new TuneableSimpleMotorFeedforward(kSSim, kVSim, kASim)
            : new TuneableSimpleMotorFeedforward(kS, kV, kA);

    public Shooter() {
        fieldsTable.update();

        TuneablesManager.add("Shooter", (Tuneable) this);
    }

    @Override
    public void periodic() {
        fieldsTable.recordOutput("upperRollerSpeedRPM", getUpperRollerSpeedRPM());
        fieldsTable.recordOutput("lowerRollerSpeedRPM", getLowerRollerSpeedRPM());
    }

    public void setVoltage(double upperRollerVoltage, double lowerRollerVoltage) {
        fieldsTable.recordOutput("upperRollerVolatgeDemend", upperRollerVoltage);
        fieldsTable.recordOutput("lowerRollerVolatgeDemend", lowerRollerVoltage);
        io.setUpperRollerVoltage(
                MathUtil.clamp(upperRollerVoltage, -ShooterConstants.MAX_VOLTAGE, ShooterConstants.MAX_VOLTAGE));
        io.setLowerRollerVoltage(
                MathUtil.clamp(lowerRollerVoltage, -ShooterConstants.MAX_VOLTAGE, ShooterConstants.MAX_VOLTAGE));
    }

    public void stop() {
        setVoltage(0, 0);
    }

    public double getUpperRollerSpeedRPM() {
        return io.upperRollerSpeedRPM.getAsDouble();
    }

    public double getLowerRollerSpeedRPM() {
        return io.lowerRollerSpeedRPM.getAsDouble();
    }

    public boolean isAtSpeed(double targetUpperSpeedRPM, double targetLowerSpeedRPM) {
        return (getUpperRollerSpeedRPM() >= targetUpperSpeedRPM - 10
                && getUpperRollerSpeedRPM() <= targetUpperSpeedRPM + 10)
                && (getLowerRollerSpeedRPM() >= targetLowerSpeedRPM - 10
                        && getLowerRollerSpeedRPM() <= targetLowerSpeedRPM + 10);
    }

    public double calculateVoltageForUpperSpeedRPM(double currentSpeed, double targetSpeedRPM) {
        return (pidUpper.calculate(currentSpeed, targetSpeedRPM) + upperFeedforward.calculate(targetSpeedRPM)) / 473;
    }

    public double calculateLowerSpeedToVoltage(double currentSpeed, double targetSpeedRPM) {
        return (pidLower.calculate(currentSpeed, targetSpeedRPM) + lowerFeedforward.calculate(targetSpeedRPM)) / 473;
    }

    public void resetPID() {
        pidUpper.reset();
        pidLower.reset();
    }

    @Override
    public void initTuneable(TuneableBuilder builder) {
        builder.addChild("PID upper", pidUpper);
        builder.addChild("PID lower", pidLower);
        builder.addChild("feedforward upper", upperFeedforward);
        builder.addChild("feedforward lower", lowerFeedforward);
    }
}