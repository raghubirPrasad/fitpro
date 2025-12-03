package wellnessapp.exceptions;

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

