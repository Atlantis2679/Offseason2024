package frc.lib.valueholders;

import edu.wpi.first.math.trajectory.TrapezoidProfile.State;

public class ValueHolder<T> {
    private T value;

    public ValueHolder(T value){
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public Object calculate(double d, ValueHolder<State> referenceState, double desiredAngleDeg) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculate'");
    }
}
