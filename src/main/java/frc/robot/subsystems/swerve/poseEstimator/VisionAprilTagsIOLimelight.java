package frc.robot.subsystems.swerve.poseEstimator;

import frc.lib.logfields.LogFieldsTable;
import frc.robot.utils.LimelightHelpers;
import edu.wpi.first.math.geometry.Pose3d;

public class VisionAprilTagsIOLimelight extends VisionAprilTagsIO {

    private LimelightHelpers.PoseEstimate limelightResults;
    private final String limelightName;

    public VisionAprilTagsIOLimelight(LogFieldsTable fieldsTable, String limelightName) {
        super(fieldsTable);
        this.limelightName = limelightName;
    }

    @Override
    public void periodicBeforeFields() {
        limelightResults = LimelightHelpers.getBotPoseEstimate_wpiBlue(limelightName);
    }

    @Override
    protected double getCameraTimestampSeconds() {
        return limelightResults.timestampSeconds;
    }

    @Override
    protected Pose3d getRobotPose() {
        Pose3d estimate = new Pose3d(limelightResults.pose);
        return estimate != null ? estimate : new Pose3d();
    }

    @Override
    protected boolean getHasNewRobotPose() {
        return limelightResults.tagCount > 0;
    }
}
