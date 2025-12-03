package wellnessapp.exceptions;

/**
 * Exception thrown when special characters are entered in fields that don't allow them
 * Demonstrates: User-defined exceptions, specific exception types
 */
public class SpecialCharacterException extends Exception {
    private String message;
    
    public SpecialCharacterException(String message) {
        super(message);
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}

