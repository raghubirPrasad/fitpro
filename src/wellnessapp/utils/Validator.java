package wellnessapp.utils;

import wellnessapp.exceptions.InvalidInputException;
import wellnessapp.exceptions.NegativeValueException;
import wellnessapp.exceptions.DecimalValueException;
import wellnessapp.exceptions.NumericValueException;
import wellnessapp.exceptions.SpecialCharacterException;
import wellnessapp.exceptions.ZeroValueException;

/**
 * Validator class for input validation
 * Demonstrates: Static methods, exception handling, multiple exception types
 */
public class Validator {
    
    // Validation for steps and age (no decimals, no negatives)
    // Note: Steps can be zero, but age cannot. Use parseAge() for age validation.
    public static int parseStepsOrAge(String input, String fieldName) throws DecimalValueException, NegativeValueException, InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
        
        String trimmed = input.trim();
        
        // Check for decimal values
        if (trimmed.contains(".")) {
            throw new DecimalValueException(fieldName + " cannot have decimal values. Please enter a whole number.");
        }
        
        try {
            int value = Integer.parseInt(trimmed);
            if (value < 0) {
                throw new NegativeValueException(fieldName + " cannot be negative. Please enter a positive value.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " must be a valid integer");
        }
    }
    
    // Validation for age (no decimals, no negatives, no zero)
    public static int parseAge(String input, String fieldName) throws DecimalValueException, NegativeValueException, ZeroValueException, InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
        
        String trimmed = input.trim();
        
        // Check for decimal values
        if (trimmed.contains(".")) {
            throw new DecimalValueException(fieldName + " cannot have decimal values. Please enter a whole number.");
        }
        
        try {
            int value = Integer.parseInt(trimmed);
            if (value < 0) {
                throw new NegativeValueException(fieldName + " cannot be negative. Please enter a positive value.");
            }
            if (value == 0) {
                throw new ZeroValueException(fieldName + " cannot be zero. Please enter a value greater than zero.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " must be a valid integer");
        }
    }
    
    // Validation for other integer fields (no decimals, no negatives)
    public static int parsePositiveInteger(String input, String fieldName) throws DecimalValueException, NegativeValueException, InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
        
        String trimmed = input.trim();
        
        // Check for decimal values
        if (trimmed.contains(".")) {
            throw new DecimalValueException(fieldName + " cannot have decimal values. Please enter a whole number.");
        }
        
        try {
            int value = Integer.parseInt(trimmed);
            if (value < 0) {
                throw new NegativeValueException(fieldName + " cannot be negative. Please enter a positive value.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " must be a valid integer");
        }
    }
    
    // Validation for double fields (no negatives, but allows decimals)
    // Note: This method allows zero. Use parsePositiveDoubleNoZero for fields that don't allow zero.
    public static double parsePositiveDouble(String input, String fieldName) throws NegativeValueException, InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
        
        try {
            double value = Double.parseDouble(input.trim());
            if (value < 0) {
                throw new NegativeValueException(fieldName + " cannot be negative. Please enter a positive value.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " must be a valid number");
        }
    }
    
    // Validation for double fields that don't allow zero (e.g., height, weight)
    public static double parsePositiveDoubleNoZero(String input, String fieldName) throws NegativeValueException, ZeroValueException, InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
        
        try {
            double value = Double.parseDouble(input.trim());
            if (value < 0) {
                throw new NegativeValueException(fieldName + " cannot be negative. Please enter a positive value.");
            }
            if (value == 0) {
                throw new ZeroValueException(fieldName + " cannot be zero. Please enter a value greater than zero.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " must be a valid number");
        }
    }
    
    // Validation for name field (only alphabets, no numbers, no special characters)
    public static String validateName(String input, String fieldName) throws NumericValueException, SpecialCharacterException, InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
        
        String trimmed = input.trim();
        
        // Check for numeric values
        if (trimmed.matches(".*\\d.*")) {
            throw new NumericValueException(fieldName + " cannot contain numbers. Please enter only alphabets.");
        }
        
        // Check for special characters (allow only letters and spaces)
        if (!trimmed.matches("^[a-zA-Z\\s]+$")) {
            throw new SpecialCharacterException(fieldName + " cannot contain special characters. Please enter only alphabets.");
        }
        
        return trimmed;
    }
    
    // Validation for username field (alphabets and numbers only, no special characters)
    public static String validateUsername(String input, String fieldName) throws SpecialCharacterException, InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
        
        String trimmed = input.trim();
        
        // Check for special characters (allow only letters, numbers, and underscore)
        if (!trimmed.matches("^[a-zA-Z0-9_]+$")) {
            throw new SpecialCharacterException(fieldName + " cannot contain special characters. Only alphabets, numbers, and underscore are allowed.");
        }
        
        return trimmed;
    }
    
    // Validation for activity/item name fields (alphabets and numbers only, no special characters, no underscore)
    public static String validateAlphabetsAndNumbers(String input, String fieldName) throws SpecialCharacterException, InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
        
        String trimmed = input.trim();
        
        // Check for special characters (allow only letters and numbers, no special characters including underscore)
        if (!trimmed.matches("^[a-zA-Z0-9\\s]+$")) {
            throw new SpecialCharacterException(fieldName + " cannot contain special characters. Only alphabets and numbers are allowed.");
        }
        
        return trimmed;
    }
    
    // Legacy methods for backward compatibility (now use new exceptions)
    public static void validatePositiveInteger(String input, String fieldName) throws InvalidInputException {
        try {
            parsePositiveInteger(input, fieldName);
        } catch (DecimalValueException | NegativeValueException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }
    
    public static void validatePositiveDouble(String input, String fieldName) throws InvalidInputException {
        try {
            parsePositiveDouble(input, fieldName);
        } catch (NegativeValueException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }
}

