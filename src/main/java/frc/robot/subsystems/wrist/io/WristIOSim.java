package frc.robot.subsystems.wrist.io;

import static frc.robot.subsystems.wrist.WristConstants.*;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.lib.logfields.LogFieldsTable;

public class WristIOSim extends WristIO {
    private final LogFieldsTable fieldsTable = new LogFieldsTable("Wrist_sim");

    Mechanism2d wrist_mech = new Mechanism2d(5, 5);
    MechanismRoot2d root = wrist_mech.getRoot("Wrist", 2.5, 2.5);
    MechanismLigament2d m_wrist = root.append(new MechanismLigament2d("wrist", 0.5, 90, 6, new Color8Bit(Color.kPurple)));
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
        double angle = Math.toDegrees(wristMotor.getAngleRads());
        m_wrist.setAngle(angle);
        fieldsTable.recordOutput("Wrist_Sim_Mech", wrist_mech);
        return angle;
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
    public void setSpeed(double speed) {
        Logger.recordOutput("set speed", speed);
        wristMotor.setInput(speed);
    }
}
