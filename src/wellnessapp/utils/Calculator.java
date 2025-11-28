package wellnessapp.utils;

/**
 * Calculator class for health metrics
 * Demonstrates: Static methods, utility classes
 */
public class Calculator {
    
    // Static method for BMI calculation
    public static double calculateBMI(double weight, double height) {
        // BMI = weight (kg) / (height (m))^2
        double heightInMeters = height / 100.0; // assuming height in cm
        return weight / (heightInMeters * heightInMeters);
    }
    
    // Static method for BMR calculation (Mifflin-St Jeor Equation)
    public static double calculateBMR(double weight, double height, int age, boolean isMale) {
        // BMR = 10 * weight(kg) + 6.25 * height(cm) - 5 * age(years) + s
        // s = +5 for males, -161 for females
        double bmr = 10 * weight + 6.25 * height - 5 * age;
        return isMale ? bmr + 5 : bmr - 161;
    }
    
    // Static method for TDEE (Total Daily Energy Expenditure)
    public static double calculateTDEE(double bmr, double activityLevel) {
        // activityLevel: 1.2 (sedentary), 1.375 (light), 1.55 (moderate), 1.725 (active), 1.9 (very active)
        return bmr * activityLevel;
    }
}

