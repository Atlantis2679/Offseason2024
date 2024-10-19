package frc.robot.allcommands;

import frc.robot.utils.LinearInterpolation;
import static frc.robot.allcommands.ShootingMeasurments.*;
import static frc.robot.Constants.*;

import java.util.ArrayList;
import java.util.List;

import com.pathplanner.lib.util.GeometryUtil;

import edu.wpi.first.math.geometry.Pose2d;

public class ShootingCalculator {
    private final LinearInterpolation pivotAngleDegreesLinearInterpolation;
    private final LinearInterpolation upperRollerSpeedRPMLinearInterpolation;
    private final LinearInterpolation lowerRollerSpeedRPMLinearInterpolation;

    private double pivotAngleDegrees;
    private double upperRollerSpeedRPM;
    private double lowerRollerSpeedRPM;

    public ShootingCalculator() {
        List<LinearInterpolation.Point> pivotAngleDegreesPoints = new ArrayList<>();
        List<LinearInterpolation.Point> upperRollerSpeedRPMPoints = new ArrayList<>();
        List<LinearInterpolation.Point> lowerRollerSpeedRPMPoints = new ArrayList<>();

        for (ShootingState shootingState : ALL_MEASURMENTS) {
            pivotAngleDegreesPoints.add(
                    new LinearInterpolation.Point(shootingState.distanceFromTarget, shootingState.pivotAngleDegrees));
            upperRollerSpeedRPMPoints.add(
                    new LinearInterpolation.Point(shootingState.distanceFromTarget, shootingState.upperRollerRPM));
            lowerRollerSpeedRPMPoints.add(
                    new LinearInterpolation.Point(shootingState.distanceFromTarget, shootingState.lowerRollerRPM));
        }

        pivotAngleDegreesLinearInterpolation = new LinearInterpolation(pivotAngleDegreesPoints);
        upperRollerSpeedRPMLinearInterpolation = new LinearInterpolation(upperRollerSpeedRPMPoints);
        lowerRollerSpeedRPMLinearInterpolation = new LinearInterpolation(pivotAngleDegreesPoints);
    }

    public void update(Pose2d robotPose, boolean isRedAlliance) {
        Pose2d speakerPose = isRedAlliance ? GeometryUtil.flipFieldPose(robotPose) : BLUE_SPEAKER_POSE;
        double distanceFromTarget = Math.sqrt(
                Math.pow(robotPose.getX() - speakerPose.getX(), 2)
                        + Math.pow(robotPose.getY() - speakerPose.getY(), 2));

        pivotAngleDegrees = pivotAngleDegreesLinearInterpolation.calculate(distanceFromTarget);
        upperRollerSpeedRPM = upperRollerSpeedRPMLinearInterpolation.calculate(distanceFromTarget);
        lowerRollerSpeedRPM = lowerRollerSpeedRPMLinearInterpolation.calculate(distanceFromTarget);
    }

    public double getPivotAngleDegrees() {
        return pivotAngleDegrees;
    }

    public double getUpperRollerSpeedRPM() {
        return upperRollerSpeedRPM;
    }

    public double getLowerRollerSpeedRPM() {
        return lowerRollerSpeedRPM;
    }
}