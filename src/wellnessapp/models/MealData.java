package wellnessapp.models;

import java.util.ArrayList;

/**
 * MealData class to track meals and water intake
 * Demonstrates: Classes, ArrayList, constructors, accessors, mutators, abstract class inheritance
 */
public class MealData extends ListTrackingData {
    private int waterIntake; // in ml
    private int caloriesEaten;
    private int targetWater; // in ml
    private int targetCalories;
    
    public MealData() {
        super(); // Initialize entries and totalCalories from ListTrackingData
        this.waterIntake = 0;
        this.caloriesEaten = 0;
        this.targetWater = 2000; // 2 liters default
        this.targetCalories = 2000;
    }
    
    // Accessors
    public ArrayList<String> getMeals() {
        return entries; // Use entries from parent class
    }
    
    public int getWaterIntake() {
        return waterIntake;
    }
    
    public int getCaloriesEaten() {
        return caloriesEaten;
    }
    
    public int getTargetWater() {
        return targetWater;
    }
    
    public int getTargetCalories() {
        return targetCalories;
    }
    
    // Mutators
    public void addMeal(String meal, int calories) {
        addEntry(meal, calories);
    }
    
    @Override
    public void addEntry(String entry, int calories) {
        String mealEntry = entry + " - " + calories + " kcal";
        this.entries.add(mealEntry);
        this.totalCalories += calories;
        this.caloriesEaten += calories;
    }
    
    public void removeMeal(int index) {
        removeEntry(index);
    }
    
    @Override
    public void removeEntry(int index) {
        if (index >= 0 && index < entries.size()) {
            String mealEntry = entries.get(index);
            int calories = parseCaloriesFromEntry(mealEntry);
            this.totalCalories = Math.max(0, this.totalCalories - calories);
            this.caloriesEaten = Math.max(0, this.caloriesEaten - calories);
            entries.remove(index);
        }
    }
    
    public void setWaterIntake(int waterIntake) {
        this.waterIntake = waterIntake;
    }
    
    public void setTargetWater(int targetWater) {
        this.targetWater = targetWater;
    }
    
    public void setTargetCalories(int targetCalories) {
        this.targetCalories = targetCalories;
    }
    
    @Override
    public void reset() {
        super.reset(); // Clear entries and totalCalories
        this.waterIntake = 0;
        this.caloriesEaten = 0;
    }
    
    @Override
    public int getCurrentValue() {
        return caloriesEaten;
    }
    
    @Override
    public int getTargetValue() {
        return targetCalories;
    }
}

