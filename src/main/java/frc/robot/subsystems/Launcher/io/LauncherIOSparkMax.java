package frc.robot.subsystems.Launcher.io;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.CANSparkLowLevel;

import frc.lib.logfields.LogFieldsTable;
import static frc.robot.RobotMap.*;

public class LauncherIOSparkMax extends LauncherIO {

    private final DigitalInput beambreak = new DigitalInput(DIO.BEAM_BREAK_ID);
    private final CANSparkMax launcherMotor = new CANSparkMax(CANBUS.LAUNCHER_MOTOR_ID,
            CANSparkLowLevel.MotorType.kBrushless);

    public LauncherIOSparkMax(LogFieldsTable fieldsTable) {
        super(fieldsTable);
    }

    @Override
    public boolean getIsNoteInside() {
        return beambreak.get();
    }

    @Override
    public void setSpeed(double speed) {
        launcherMotor.set(speed);
    }

}
