package frc.robot.subsystems.swerve.poseEstimator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import frc.lib.logfields.LogFieldsTable;
import static frc.robot.RobotMap.*;

public class PoseEstimatorWithVision {
    private final SwerveDrivePoseEstimator swervePoseEstimator;
    private final LogFieldsTable fieldsTable; // not sure needed

    private final static boolean ignoreFarVisionEstimate = false; // fix this!

    private final Map<String, VisionAprilTagsIO> cameras = new HashMap<String, VisionAprilTagsIO>();

    public PoseEstimatorWithVision(LogFieldsTable fieldsTable, Rotation2d gyroCurrentAngle, SwerveModulePosition[] modulePositions, SwerveDriveKinematics kinematics, Pose2d initialPose) {
        this.fieldsTable = fieldsTable;
        swervePoseEstimator = new SwerveDrivePoseEstimator(kinematics, gyroCurrentAngle, modulePositions, initialPose);
        AprilTagFieldLayout fieldLayout;
        try {
            fieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2024Crescendo.m_resourceFile);
        } catch (IOException e) {
            e.printStackTrace(); // maybe need to be changed
            throw new RuntimeException();
        }
        
        cameras.put(FRONT_PHOTON_CAMERA_NAME, new VisionAprilTagsIOPhoton(fieldsTable, new PhotonCamera(FRONT_PHOTON_CAMERA_NAME), fieldLayout));
        cameras.put(BACK_LIMELIGHT_CAMERA_NAME, new VisionAprilTagsIOLimelight(fieldsTable, BACK_LIMELIGHT_CAMERA_NAME));        
    }

    public void update(Rotation2d gyroCurrentAngle, SwerveModulePosition[] modulePositions) {
        swervePoseEstimator.update(gyroCurrentAngle, modulePositions);

        cameras.forEach((String cameraName, VisionAprilTagsIO io) -> {
            fieldsTable.recordOutput("Diffrence between swerve and vision estimate", getSwerveToVisionDiff(io));
            if(getSwerveToVisionDiff(io) < PoseEstimatorConstants.VISION_THRESHOLD_DISTANCE_M || ignoreFarVisionEstimate) {
                swervePoseEstimator.addVisionMeasurement(io.getRobotPose().toPose2d(), io.getCameraTimestampSeconds());
            }
        });
    }

    public void resetPosition(Rotation2d gyroCurrentAngle, SwerveModulePosition[] modulePositions, Pose2d newPose) {
        swervePoseEstimator.resetPosition(gyroCurrentAngle, modulePositions, newPose);
    }

    public Pose2d getPose() {
        return swervePoseEstimator.getEstimatedPosition();
    }

    public double getSwerveToVisionDiff(VisionAprilTagsIO io) {
        return PhotonUtils.getDistanceToPose(swervePoseEstimator.getEstimatedPosition(), io.getRobotPose().toPose2d());
    }
}
