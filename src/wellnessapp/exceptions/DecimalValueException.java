package wellnessapp.exceptions;

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

