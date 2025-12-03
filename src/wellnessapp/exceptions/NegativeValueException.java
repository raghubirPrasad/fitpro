package wellnessapp.exceptions;

/**
 * Exception thrown when a negative value is entered
 * Demonstrates: User-defined exceptions, specific exception types
 */
public class NegativeValueException extends Exception {
    private String message;
    
    public NegativeValueException(String message) {
        super(message);
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}

