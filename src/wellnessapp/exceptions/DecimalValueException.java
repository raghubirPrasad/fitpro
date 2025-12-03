package wellnessapp.exceptions;

/**
 * Exception thrown when decimal values are entered in fields that only accept integers
 * Demonstrates: User-defined exceptions, specific exception types
 */
public class DecimalValueException extends Exception {
    private String message;
    
    public DecimalValueException(String message) {
        super(message);
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}

