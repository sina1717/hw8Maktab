package eceptions;

public class InvalidNationalCodeException extends RuntimeException{

    public InvalidNationalCodeException(String message) {
        super(message);
    }
}
