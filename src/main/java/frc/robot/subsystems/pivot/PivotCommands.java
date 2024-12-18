package frc.robot.subsystems.pivot;

import static frc.robot.subsystems.pivot.PivotConstants.MAX_ANGLE_DEGREE;
import static frc.robot.subsystems.pivot.PivotConstants.MIN_ANGLE_DEGREE;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.valueholders.ValueHolder;

public class PivotCommands extends Command {
    private final Pivot pivot;

    public PivotCommands(Pivot pivot) {
        this.pivot = pivot;
        this.addRequirements(pivot);
    }

    public Command moveToAngle(DoubleSupplier desiredAngleDeg) {
        ValueHolder<TrapezoidProfile.State> referenceState = new ValueHolder<TrapezoidProfile.State>(null);
        return pivot.runOnce(() -> {
            pivot.resetPID();
            referenceState.set(new TrapezoidProfile.State(pivot.getAbsoluteAngleDegrees(), pivot.getVelocity()));
        }).andThen(pivot.run(() -> {
            referenceState.set(pivot.calculateTrapezoidProfile(
                    0.02,
                    referenceState.get(),
                    new TrapezoidProfile.State(desiredAngleDeg.getAsDouble(), 0)));

            double voltage = pivot.calculateFeedforward(
                    referenceState.get().position,
                    referenceState.get().velocity,
                    true);

            pivot.setPivotVoltage(voltage);
        })).finallyDo(pivot::stop).withName("pivotMoveToAngle");
    }

    public Command moveToAngle(double angle) {
        return moveToAngle(() -> angle);
    }

    public Command manualController(DoubleSupplier pivotSpeed) {
        return pivot.run(() -> {
            double feedforwardResult = pivot.calculateFeedforward(
                    pivot.getAbsoluteAngleDegrees(),
                    0,
                    false);
            double demandPivotSpeed = pivotSpeed.getAsDouble();
            if ((pivot.getAbsoluteAngleDegrees() > MAX_ANGLE_DEGREE && demandPivotSpeed > 0)
                    || (pivot.getAbsoluteAngleDegrees() < MIN_ANGLE_DEGREE && demandPivotSpeed < 0)) {
                demandPivotSpeed = 0;
            }
            pivot.setPivotVoltage(
                    feedforwardResult + demandPivotSpeed * PivotConstants.MANUAL_SPEED_MULTIPLIER);
        }).finallyDo(pivot::stop).withName("pivotManualController");
    }
}