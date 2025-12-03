package wellnessapp.exceptions;

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

