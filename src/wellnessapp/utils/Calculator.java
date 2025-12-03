package wellnessapp.utils;

public class Calculator {
    public static double calculateBMI(double weight, double height) {
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }
    
    public static double calculateBMR(double weight, double height, int age, boolean isMale) {
        double bmr = 10 * weight + 6.25 * height - 5 * age;
        return isMale ? bmr + 5 : bmr - 161;
    }
    
    public static double calculateTDEE(double bmr, double activityLevel) {
        return bmr * activityLevel;
    }
    
    public static double calculateTDEE(double weight, double height, int age, boolean isMale, double activityLevel) {
        double bmr = calculateBMR(weight, height, age, isMale);
        return calculateTDEE(bmr, activityLevel);
    }
}
