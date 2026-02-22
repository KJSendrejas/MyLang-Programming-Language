package main.java.mylang.interpreter;

public class Value {
    public Double value; // store as double for simplicity

    public Value(double v) {
        this.value = v;
    }
    public Value(Double v) {
        this.value = v;
    }

    @Override
    public String toString() {
        return value == null ? "void" : value.toString();
    }
}