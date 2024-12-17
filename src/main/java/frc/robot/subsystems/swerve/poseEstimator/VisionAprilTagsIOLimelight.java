package frc.robot.subsystems.swerve.poseEstimator;

import frc.lib.logfields.LogFieldsTable;
import frc.robot.utils.LimelightHelpers;
import frc.robot.utils.LimelightHelpers.LimelightResults;
import frc.robot.utils.LimelightHelpers.PoseEstimate;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;

public class VisionAprilTagsIOLimelight extends VisionAprilTagsIO {
    String cameraName;
    LimelightResults results;


    protected VisionAprilTagsIOLimelight(LogFieldsTable fieldsTable, String cameraName) {
        super(fieldsTable);
        this.cameraName = cameraName;
        Transform3d robotToCamTramsform = PoseEstimatorConstants.ROBOT_TO_CAMERA_TRANSFORM_LIMELIGHT_BACK;
        LimelightHelpers.setCameraPose_RobotSpace(cameraName, robotToCamTramsform.getTranslation().getX(),
            robotToCamTramsform.getTranslation().getY(), robotToCamTramsform.getTranslation().getZ(),
            robotToCamTramsform.getRotation().getX(), robotToCamTramsform.getRotation().getY(),
            robotToCamTramsform.getRotation().getZ());
    }

    @Override
    protected void periodicBeforeFields() {
        results = LimelightHelpers.getLatestResults(cameraName);
    }

    @Override
    protected double getCameraTimestampSeconds() {
        PoseEstimate tempEstimate = LimelightHelpers.getBotPoseEstimate_wpiBlue(cameraName);
        return tempEstimate != null && results != null? tempEstimate.timestampSeconds: 0;
    }

    @Override
    protected boolean getHasNewRobotPose() {
        return results != null? results.targets_Fiducials.length > 0: false;
    }

    @Override
    protected int getVisibleTargetsCount() {
        return results != null? results.targets_Fiducials.length: 0;
    }

    @Override
    protected Pose3d getRobotPose() {
        if (results != null) {
            return results.getBotPose3d() != null? results.getBotPose3d_wpiBlue(): new Pose3d();
        } else return new Pose3d();
    }
}
