package frc.robot.subsystems.swerve.poseEstimator;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose3d;
import frc.lib.logfields.IOBase;
import frc.lib.logfields.LogFieldsTable;

public abstract class VisionAprilTagsIO extends IOBase {
    public final Supplier<Pose3d> poseEstimate = fields.addObject("pose esitmate", this::getRobotPose, new Pose3d());
    public final DoubleSupplier cameraTimestamp = fields.addDouble("camera timestamp", this::getCameraTimestampSeconds);
    public final BooleanSupplier hasNewRobotPose = fields.addBoolean("has new robot pose", this::getHasNewRobotPose);
    public final LongSupplier visibleTargetsCount = fields.addInteger("visible targets", this::getVisibleTargetsCount);

    protected VisionAprilTagsIO(LogFieldsTable fieldsTable) {
        super(fieldsTable);
    }

    protected abstract double getCameraTimestampSeconds();

    protected abstract boolean getHasNewRobotPose();

    protected abstract int getVisibleTargetsCount();

    protected abstract Pose3d getRobotPose();
}
