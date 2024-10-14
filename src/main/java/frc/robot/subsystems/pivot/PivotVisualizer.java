package frc.robot.subsystems.pivot;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.lib.logfields.LogFieldsTable;

public class PivotVisualizer {
    private final LogFieldsTable fieldsTable;
    private final String name;

    private final Mechanism2d pivotMech = new Mechanism2d(5, 5);
    private final MechanismRoot2d root = pivotMech.getRoot("Pivot", 2.5, 2.5);
    private final MechanismLigament2d pivotLigament;

    public PivotVisualizer(LogFieldsTable fieldsTable, String name, Color8Bit color) {
        this.fieldsTable = fieldsTable;
        this.name = name;

        pivotLigament = root
                .append(new MechanismLigament2d("pivot", 2, 90, 6, color));
    }

    public void update(double angleDegrees) {
        pivotLigament.setAngle(angleDegrees);
        fieldsTable.recordOutput(name, pivotMech);
    }
}
