package wellnessapp.exceptions;

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

