package wellnessapp.exceptions;

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

