package wellnessapp.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * MealData class to track meals and water intake
 * Demonstrates: Classes, ArrayList, constructors, accessors, mutators
 */
public class MealData implements Serializable {
    private ArrayList<String> meals;
    private int waterIntake; // in ml
    private int caloriesEaten;
    private int targetWater; // in ml
    private int targetCalories;
    
    public MealData() {
        this.meals = new ArrayList<String>();
        this.waterIntake = 0;
        this.caloriesEaten = 0;
        this.targetWater = 2000; // 2 liters default
        this.targetCalories = 2000;
    }
    
    // Accessors
    public ArrayList<String> getMeals() {
        return meals;
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
        this.meals.add(meal);
        this.caloriesEaten += calories;
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
    
    public void reset() {
        this.meals.clear();
        this.waterIntake = 0;
        this.caloriesEaten = 0;
    }
}

