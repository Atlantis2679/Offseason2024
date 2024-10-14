package frc.robot.subsystems.pivot;

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

    public Command MoveToAngle(DoubleSupplier desiredAngleDeg) {
        ValueHolder<TrapezoidProfile.State> referenceState = new ValueHolder<TrapezoidProfile.State>(null);
        return pivot.runOnce(() -> {
            pivot.resetPID();
            referenceState.set(new TrapezoidProfile.State(pivot.getAbsoluteAngleDegrees(), 0));
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
        }));
    }

    public Command manualController(DoubleSupplier pivotSpeed) {
        return pivot.run(() -> {
            double feedforwardResult = pivot.calculateFeedforward(
                    pivot.getAbsoluteAngleDegrees(),
                    0,
                    false);
            pivot.setPivotVoltage(
                    feedforwardResult + pivotSpeed.getAsDouble() * PivotConstants.MANUAL_SPEED_MULTIPLIER);
        });
    }

}