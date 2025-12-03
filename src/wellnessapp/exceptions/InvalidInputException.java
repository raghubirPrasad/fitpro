package wellnessapp.exceptions;

public class InvalidInputException extends Exception {
    private String message;
    
    public InvalidInputException(String message) {
        super(message);
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
