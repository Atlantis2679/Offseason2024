package frc.robot.subsystems.swerve.poseEstimator;

import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose3d;
import frc.lib.logfields.LogFieldsTable;

public class VisionAprilTagsIOPhoton extends VisionAprilTagsIO {
    private final PhotonCamera camera;
    private final PhotonPoseEstimator poseEstimator;
    private Optional<EstimatedRobotPose> estimatedRobotPose;
    private PhotonPipelineResult pipline;

    protected VisionAprilTagsIOPhoton(LogFieldsTable fieldsTable, PhotonCamera camera, AprilTagFieldLayout aprilTagFieldLayout) {
        super(fieldsTable);
        this.camera = camera;
        this.poseEstimator = new PhotonPoseEstimator(aprilTagFieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, camera, PoseEstimatorConstants.ROBOT_TO_CAMERA_TRANSFORM_PHOTON_FRONT);
    }

    @Override
    protected void periodicBeforeFields() {
        pipline = camera.getLatestResult();
        estimatedRobotPose = poseEstimator.update(pipline);
    }

    @Override
    protected double getCameraTimestampSeconds() {
        return pipline != null? pipline.getTimestampSeconds(): 0;
    }

    @Override
    protected boolean getHasNewRobotPose() {
        return pipline != null? pipline.hasTargets(): false;
    }

    @Override
    protected int getVisibleTargetsCount() {
        return pipline != null? pipline.getTargets().size(): 0;
    }

    @Override
    protected Pose3d getRobotPose() {
        return estimatedRobotPose.isPresent()? estimatedRobotPose.get().estimatedPose: new Pose3d();
    }    
}