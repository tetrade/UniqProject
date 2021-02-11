package main;

public class MinStringException extends Exception {
    public MinStringException() {
    }

    public MinStringException(String message) {
        super(message);
    }

    public MinStringException(String message, Throwable cause) {
        super(message, cause);
    }
}
