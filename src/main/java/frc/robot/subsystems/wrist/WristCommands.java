// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.wrist;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.lib.valueholders.ValueHolder;
import frc.robot.subsystems.wrist.Wrist;

/** Add your docs here. */
public class WristCommands {
    private final Wrist wrist;

    public Command MoveToAngle(double desiredAngleDeg){
        ValueHolder<TrapezoidProfile.State> refrenceState = new ValueHolder<TrapezoidProfile.State>(null);
        return wrist.runOnce(() -> {
            wrist.resetPID();
            refrenceState.set(new TrapezoidProfile.State(wrist.getAbsoluteAngle(), 0));
        }).andThen(() -> {
            
            double voltage = wrist.calculateFeedforward(refrenceState.get().position, refrenceState.get().velocity, true) + refrenceState.get().velocity;
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
}