package frc.robot.utils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.lib.tuneables.Tuneable;
import frc.lib.tuneables.TuneableBuilder;
import frc.lib.valueholders.DoubleHolder;

public class PrimitiveRotationalSensorHelper implements Tuneable {
    private double measuredAngle;
    private double offset;
    private double previousAngle;
    private double currentTimeSec;
    private double previousTimeSec;
    private double deltaTime;

    private boolean continousWrapEnabled;
    private double continousWrapUpperBound;
    private double continousWrapLowerBound;
    private double fullRotation;

    public PrimitiveRotationalSensorHelper(double initialMeasuredAngle, double initialOffset) {
        measuredAngle = initialMeasuredAngle;
        offset = initialOffset;
        previousTimeSec = Timer.getFPGATimestamp();
    }

    public PrimitiveRotationalSensorHelper(double initalMeasuredAngle) {
        this(initalMeasuredAngle, 0);
    }

    public void update(double measuredAngle) {
        currentTimeSec = Timer.getFPGATimestamp();
        deltaTime = currentTimeSec - previousTimeSec;
        previousAngle = getAngle();
        this.measuredAngle = measuredAngle;
        previousTimeSec = Timer.getFPGATimestamp();
    }

    public double getMeasuredAngle() {
        return measuredAngle;
    }

    public double getVelocity() {
        if(deltaTime !=0) return (getAngle() - previousAngle) / deltaTime;
        else {
            DriverStation.reportWarning("You should not request velocity after no time passed", true);
            return 0;
        }
    }

    public double getAngle() {
        double angle = measuredAngle - offset;
        if (continousWrapEnabled) {
            while (angle > continousWrapUpperBound) {
                angle -= fullRotation;
            }
            while (angle < continousWrapLowerBound) {
                angle += fullRotation;
            }
        }
        return angle;
    }

    public void resetAngle(double newAngle) {
        offset = measuredAngle - newAngle;
    }

    public void enableContinousWrap(double upperBound, double fullRotation) {
        continousWrapEnabled = true;
        this.fullRotation = fullRotation;
        continousWrapUpperBound = upperBound;
        continousWrapLowerBound = upperBound - fullRotation;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }


    public double getOffset() {
        return offset;
    }

    @Override
    public void initTuneable(TuneableBuilder builder) {
        DoubleHolder angleToResetDegrees = new DoubleHolder(0);
        builder.addDoubleProperty("raw angle measurment",
                this::getMeasuredAngle, null);

        builder.addDoubleProperty("calculated angle", this::getAngle, null);
        builder.addDoubleProperty("offset", this::getOffset,
                this::setOffset);

        builder.addDoubleProperty("angle to reset", angleToResetDegrees::get,
                angleToResetDegrees::set);

        builder.addChild("reset!", new InstantCommand(() -> {
            resetAngle(angleToResetDegrees.get());
        }).ignoringDisable(true));
    }
}