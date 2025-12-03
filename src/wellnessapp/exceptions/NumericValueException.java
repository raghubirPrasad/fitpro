package wellnessapp.exceptions;

/**
 * Exception thrown when numeric values are entered in text fields that only accept alphabets
 * Demonstrates: User-defined exceptions, specific exception types
 */
public class NumericValueException extends Exception {
    private String message;
    
    public NumericValueException(String message) {
        super(message);
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}

