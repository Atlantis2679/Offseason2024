package frc.robot.subsystems.swerve.poseEstimator;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose3d;
import frc.lib.logfields.IOBase;
import frc.lib.logfields.LogFieldsTable;

public abstract class VisionAprilTagsIO extends IOBase {
    public final Supplier<Pose3d> poseEstimate = fields.addObject("pose esitmate", this::getRobotPose, new Pose3d());
    public final BooleanSupplier cameraTimestamp = fields.addBoolean("camera timestamp", this::getCameraTimestampSeconds);

    protected VisionAprilTagsIO(LogFieldsTable fieldsTable) {
        super(fieldsTable);
    }

    protected abstract double getCameraTimestampSeconds();

    protected abstract Pose3d getRobotPose();
}
