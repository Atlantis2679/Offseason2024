package frc.robot.subsystems.launcher.io;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.CANSparkLowLevel;

import frc.lib.logfields.LogFieldsTable;
import frc.robot.RobotMap.CANBUS;
import frc.robot.RobotMap.DIO;
import frc.robot.subsystems.launcher.LauncherConstants;

public class LauncherIOSparkMax extends LauncherIO {
    private final DigitalInput beambreak = new DigitalInput(DIO.BEAM_BREAK_ID);
    private final CANSparkMax launcherMotor = new CANSparkMax(CANBUS.LAUNCHER_MOTOR_ID,
            CANSparkLowLevel.MotorType.kBrushless);

    public LauncherIOSparkMax(LogFieldsTable fieldsTable) {
        super(fieldsTable);
        launcherMotor.restoreFactoryDefaults();
        launcherMotor.setSmartCurrentLimit(LauncherConstants.LAUNCHER_CURRENT_LIMIT);
        launcherMotor.setIdleMode(IdleMode.kBrake);
        launcherMotor.setInverted(true);
    }

    @Override
    public boolean getIsNoteInside() {
        return !beambreak.get();
    }

    @Override
    public double getMotorCurrent() {
        return launcherMotor.getOutputCurrent();
    }

    @Override
    public void setSpeed(double speed) {
        launcherMotor.setVoltage(speed * 12);
    }

}
