package frc.robot.subsystems.wrist;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.lib.logfields.LogFieldsTable;

public class WristVisualizer {
    private final LogFieldsTable fieldsTable;
    private final String name;

    private final Mechanism2d wristMech = new Mechanism2d(5, 5);
    private final MechanismRoot2d root = wristMech.getRoot("Wrist", 2.5, 2.5);
    private final MechanismLigament2d wristLigament;

    public WristVisualizer(LogFieldsTable fieldsTable, String name, Color8Bit color) {
        this.fieldsTable = fieldsTable;
        this.name = name;

        wristLigament = root
                .append(new MechanismLigament2d("wrist", 2, 90, 6, color));
    }

    public void update(double angleDegrees) {
        wristLigament.setAngle(angleDegrees);
        fieldsTable.recordOutput(name, wristMech);
    }
}
