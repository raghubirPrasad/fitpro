package wellnessapp.utils;

import wellnessapp.exceptions.InvalidInputException;

/**
 * Validator class for input validation
 * Demonstrates: Static methods, exception handling
 */
public class Validator {
    
    // Static method for validation
    public static void validatePositiveInteger(String input, String fieldName) throws InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
        
        try {
            int value = Integer.parseInt(input.trim());
            if (value < 0) {
                throw new InvalidInputException(fieldName + " cannot be negative");
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " must be a valid integer (no decimals allowed)");
        }
    }
    
    public static void validatePositiveDouble(String input, String fieldName) throws InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
        
        try {
            double value = Double.parseDouble(input.trim());
            if (value < 0) {
                throw new InvalidInputException(fieldName + " cannot be negative");
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " must be a valid number");
        }
    }
    
    public static int parsePositiveInteger(String input, String fieldName) throws InvalidInputException {
        validatePositiveInteger(input, fieldName);
        return Integer.parseInt(input.trim());
    }
    
    public static double parsePositiveDouble(String input, String fieldName) throws InvalidInputException {
        validatePositiveDouble(input, fieldName);
        return Double.parseDouble(input.trim());
    }
}

