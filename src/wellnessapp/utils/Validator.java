package wellnessapp.utils;

import wellnessapp.exceptions.InvalidInputException;
import wellnessapp.exceptions.NegativeValueException;
import wellnessapp.exceptions.DecimalValueException;
import wellnessapp.exceptions.NumericValueException;
import wellnessapp.exceptions.SpecialCharacterException;
import wellnessapp.exceptions.ZeroValueException;
public class Validator {
    
    public static int parseStepsOrAge(String input, String fieldName) throws DecimalValueException, NegativeValueException, InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
        
        String trimmed = input.trim();
        
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
    
    public static int parseAge(String input, String fieldName) throws DecimalValueException, NegativeValueException, ZeroValueException, InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
        
        String trimmed = input.trim();
        
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
    
    public static int parsePositiveInteger(String input, String fieldName) throws DecimalValueException, NegativeValueException, InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
        
        String trimmed = input.trim();
        
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
    
    public static String validateName(String input, String fieldName) throws NumericValueException, SpecialCharacterException, InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
        
        String trimmed = input.trim();
        
        if (trimmed.matches(".*\\d.*")) {
            throw new NumericValueException(fieldName + " cannot contain numbers. Please enter only alphabets.");
        }
        
        if (!trimmed.matches("^[a-zA-Z\\s]+$")) {
            throw new SpecialCharacterException(fieldName + " cannot contain special characters. Please enter only alphabets.");
        }
        
        return trimmed;
    }
    
    public static String validateUsername(String input, String fieldName) throws SpecialCharacterException, InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
        
        String trimmed = input.trim();
        
        if (!trimmed.matches("^[a-zA-Z0-9_]+$")) {
            throw new SpecialCharacterException(fieldName + " cannot contain special characters. Only alphabets, numbers, and underscore are allowed.");
        }
        
        return trimmed;
    }
    
    public static String validateAlphabetsAndNumbers(String input, String fieldName) throws SpecialCharacterException, InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
        
        String trimmed = input.trim();
        
        if (!trimmed.matches("^[a-zA-Z0-9\\s]+$")) {
            throw new SpecialCharacterException(fieldName + " cannot contain special characters. Only alphabets and numbers are allowed.");
        }
        
        return trimmed;
    }
    
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
