// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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
    PhotonPoseEstimator photonPoseEstimator;
    private PhotonCamera camera;
    private PhotonPipelineResult photonPipelineResult;
    Optional<EstimatedRobotPose> photonEstimatorResult;

    public VisionAprilTagsIOPhoton(LogFieldsTable fieldsTable, String cameraName, AprilTagFieldLayout tagLayout) {
        super(fieldsTable);

        camera = new PhotonCamera(cameraName);
        photonPoseEstimator = new PhotonPoseEstimator(tagLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, camera,
                PoseEstimatorConstants.ROBOT_TO_CAMERA_TRANSFORM);
    }

    @Override
    public void periodicBeforeFields() {
        photonPipelineResult = camera.getLatestResult();
        photonEstimatorResult = photonPoseEstimator.update(photonPipelineResult);
    }

    @Override
    protected double getCameraTimestampSeconds() {
        return photonPipelineResult != null ? photonPipelineResult.getTimestampSeconds() : 0;
    }

    @Override
    protected Pose3d getRobotPose() {
        EstimatedRobotPose estimate = photonEstimatorResult != null
                ? photonEstimatorResult.orElse(null)
                : null;
        return estimate != null ? estimate.estimatedPose : new Pose3d();
    }

    @Override
    protected boolean getHasNewRobotPose() {
        return photonPipelineResult != null && photonPipelineResult.hasTargets() && photonEstimatorResult.isPresent();
    }

    @Override
    protected int getVisibleTargetCount(){
        return photonPipelineResult.targets.size();
    }
}
