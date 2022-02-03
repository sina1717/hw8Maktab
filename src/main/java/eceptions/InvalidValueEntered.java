package eceptions;

public class InvalidValueEntered extends RuntimeException {
    public InvalidValueEntered(String message) {
        super(message);
    }
}
