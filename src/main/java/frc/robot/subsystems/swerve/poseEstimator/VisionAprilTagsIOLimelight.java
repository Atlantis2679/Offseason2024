package frc.robot.subsystems.swerve.poseEstimator;

import frc.lib.logfields.LogFieldsTable;
import frc.robot.utils.LimelightHelpers;
import frc.robot.utils.LimelightHelpers.LimelightResults;
import frc.robot.utils.LimelightHelpers.LimelightTarget_Fiducial;
import frc.robot.utils.LimelightHelpers.RawFiducial;

import java.util.List;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;

public class VisionAprilTagsIOLimelight extends VisionAprilTagsIO {

    private LimelightResults limelightResults;
    //private Pose3d limelightPose3d;
    private final String limelightName;

    public VisionAprilTagsIOLimelight(LogFieldsTable fieldsTable, String limelightName) {
        super(fieldsTable);
        this.limelightName = limelightName;
        LimelightHelpers.setCameraPose_RobotSpace(limelightName, 
            PoseEstimatorConstants.ROBOT_TO_CAMERA_TRANSFORM_LIMELIGHT_BACK.getTranslation().getX(),
            PoseEstimatorConstants.ROBOT_TO_CAMERA_TRANSFORM_LIMELIGHT_BACK.getTranslation().getY(),
            PoseEstimatorConstants.ROBOT_TO_CAMERA_TRANSFORM_LIMELIGHT_BACK.getTranslation().getZ(),
            PoseEstimatorConstants.ROBOT_TO_CAMERA_TRANSFORM_LIMELIGHT_BACK.getRotation().getX(),
            PoseEstimatorConstants.ROBOT_TO_CAMERA_TRANSFORM_LIMELIGHT_BACK.getRotation().getY(),
            PoseEstimatorConstants.ROBOT_TO_CAMERA_TRANSFORM_LIMELIGHT_BACK.getRotation().getZ());
    }

    @Override
    public void periodicBeforeFields() {
        limelightResults = LimelightHelpers.getLatestResults(limelightName);
    }

    @Override
    protected double getCameraTimestampSeconds() {
        return limelightResults != null ? limelightResults.timestamp_LIMELIGHT_publish : 0;
    }

    @Override
    protected Pose3d getRobotPose() {
        //Pose3d estimate = new Pose3d(limelightResults.pose);
        //return estimate != null ? estimate : new Pose3d();
        return limelightResults.getBotPose3d() != null ? limelightResults.getBotPose3d()  : new Pose3d();
    }

    @Override
    protected boolean getHasNewRobotPose() {
        return limelightResults != null && limelightResults.botpose_tagcount > 0;
    }

    @Override
    protected int getVisibleTargetCount(){
        return limelightResults != null ? (int)limelightResults.botpose_tagcount : 0;
    }

    @Override
    protected Transform3d[] getApriltagArrInRobotSpace(){
        LimelightTarget_Fiducial[] fiducials = LimelightHelpers.getLatestResults(limelightName).targets_Fiducials;
        Transform3d[] targetsArr = new Transform3d[(int) limelightResults.botpose_tagcount];
        for (int i = 0; i < (int)limelightResults.botpose_tagcount; i++) {
            targetsArr[i] = fiducials[i].getTargetPose_RobotSpace().minus(getRobotPose());
        }

        return targetsArr;
    }
}
