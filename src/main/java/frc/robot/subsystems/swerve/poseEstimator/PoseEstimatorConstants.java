// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.swerve.poseEstimator;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;

public class PoseEstimatorConstants {
    public final static Transform3d ROBOT_TO_CAMERA_TRANSFORM_PHOTON_FRONT = new Transform3d(
            new Translation3d(0, -28.6, 0),
            new Rotation3d(0, Math.toRadians(-28.6), 0));

    public final static Transform3d ROBOT_TO_CAMERA_TRANSFORM_LIMELIGHT_BACK = new Transform3d(
            new Translation3d(-40, 0, 27),
            new Rotation3d(0, Math.toRadians(-32.2), 0));
            
    public final static double VISION_THRESHOLD_DISTANCE_M = 1.5;

    public final static double STATE_TRUST_LEVEL_X = 0.1;
    public final static double STATE_TRUST_LEVEL_Y = 0.1;
    public final static double STATE_TRUST_LEVEL_Z = 0.1;

    public final static double VISION_TRUST_LEVEL_X = 0.9;
    public final static double VISION_TRUST_LEVEL_Y = 0.9;
    public final static double VISION_TRUST_LEVEL_Z = 0.9;
}
