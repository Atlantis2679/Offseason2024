package frc.robot.allcommands;

public class ShootingState {
    public final double distanceFromTarget;
    public final double pivotAngleDegrees;
    public final double upperRollerRPM;
    public final double lowerRollerRPM;

    public ShootingState(double distanceFromTarget, double pivotAngleDegrees, double upperRollerRPM, double lowerRollerRPM) {
        this.distanceFromTarget = distanceFromTarget;
        this.pivotAngleDegrees = pivotAngleDegrees;
        this.upperRollerRPM = upperRollerRPM;
        this.lowerRollerRPM = lowerRollerRPM;
    }
}