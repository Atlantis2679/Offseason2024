package frc.robot.subsystems.wrist;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.valueholders.ValueHolder;

public class WristCommands extends Command {
    private final Wrist wrist;

    public WristCommands(Wrist wrist) {
        this.wrist = wrist;
        this.addRequirements(wrist);
    }

    public Command MoveToAngle(DoubleSupplier desiredAngleDeg) {
        ValueHolder<TrapezoidProfile.State> referenceState = new ValueHolder<TrapezoidProfile.State>(null);
        return wrist.runOnce(() -> {
            wrist.resetPID();
            referenceState.set(new TrapezoidProfile.State(wrist.getAbsoluteAngleDegrees(), 0));
        }).andThen(wrist.run(() -> {
            referenceState.set(wrist.calculateTrapezoidProfile(
                    0.02,
                    referenceState.get(),
                    new TrapezoidProfile.State(desiredAngleDeg.getAsDouble(), 0)));

            double voltage = wrist.calculateFeedforward(
                    referenceState.get().position,
                    referenceState.get().velocity,
                    true);
    
            wrist.setWristVoltage(voltage);
        }));
    }

    public Command manualController(DoubleSupplier wristSpeed) {
        return wrist.run(() -> {
            double feedforwardResult = wrist.calculateFeedforward(
                    wrist.getAbsoluteAngleDegrees(),
                    0,
                    false);
            wrist.setWristVoltage(
                    feedforwardResult + wristSpeed.getAsDouble() * WristConstants.MANUAL_SPEED_MULTIPLIER);
        });
    }

}