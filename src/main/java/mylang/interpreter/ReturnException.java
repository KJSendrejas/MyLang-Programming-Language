package main.java.mylang.interpreter;

public class ReturnException extends RuntimeException {
    public Value value;
    public ReturnException(Value v) { this.value = v; }
}