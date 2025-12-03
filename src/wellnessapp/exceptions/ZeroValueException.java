package wellnessapp.exceptions;

/**
 * Exception thrown when a zero value is entered in fields that don't allow zero
 * Demonstrates: User-defined exceptions, specific exception types
 */
public class ZeroValueException extends Exception {
    private String message;
    
    public ZeroValueException(String message) {
        super(message);
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}

