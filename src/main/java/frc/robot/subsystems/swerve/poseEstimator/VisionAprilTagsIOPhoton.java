package frc.robot.subsystems.swerve.poseEstimator;

import java.util.Optional;
import java.util.OptionalInt;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import frc.lib.logfields.LogFieldsTable;

public class VisionAprilTagsIOPhoton extends VisionAprilTagsIO {
    private final AprilTagFieldLayout aprilTagFieldLayout;
    private final PhotonCamera camera;
    private final PhotonPoseEstimator poseEstimator;
    private Optional<EstimatedRobotPose> estimatedRobotPose;
    private PhotonPipelineResult pipline;

    protected VisionAprilTagsIOPhoton(LogFieldsTable fieldsTable, PhotonCamera camera, AprilTagFieldLayout aprilTagFieldLayout, Transform3d robotToCamTransform) {
        super(fieldsTable);
        this.aprilTagFieldLayout = aprilTagFieldLayout;
        this.camera = camera;
        this.poseEstimator = new PhotonPoseEstimator(aprilTagFieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, camera, robotToCamTransform);
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