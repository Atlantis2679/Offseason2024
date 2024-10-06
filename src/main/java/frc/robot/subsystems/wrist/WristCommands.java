// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.wrist;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.valueholders.ValueHolder;

/** Add your docs here. */
public class WristCommands extends Command{
    private final Wrist wrist;

    public WristCommands(Wrist wrist) {
        this.wrist = wrist;
        this.addRequirements(wrist); 
    }

    public Command MoveToAngle(double desiredAngleDeg){
        ValueHolder<TrapezoidProfile.State> referenceState = new ValueHolder<TrapezoidProfile.State>(null);
        return wrist.runOnce(() -> {
            wrist.resetPID();
            referenceState.set(new TrapezoidProfile.State(wrist.getAbsoluteAngle(), 0));
        }).andThen(() -> {
            double voltage = wrist.calculateFeedforward(referenceState.get().position, referenceState.get().velocity, true) + referenceState.get().velocity;
            wrist.setWristVoltage(voltage);
        });
    }
    
    public Command manualController(DoubleSupplier wristSpeed) {
        return wrist.run(() -> {
            double feedforwardResult = wrist.calculateFeedforward(
                    wrist.getAbsoluteAngle(),
                    0,
                    false);
            wrist.setWristVoltage(feedforwardResult + wristSpeed.getAsDouble() * WristConstants.MANUAL_SPEED_MULTIPLIER);
        });
    }
 

}