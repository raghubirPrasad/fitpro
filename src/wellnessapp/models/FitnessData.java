package wellnessapp.models;

import java.util.ArrayList;

/**
 * FitnessData class to track fitness activities
 * Demonstrates: Classes, constructors, accessors, mutators, ArrayList, abstract class inheritance
 */
public class FitnessData extends ListTrackingData {
    private int steps;
    private int caloriesBurned;
    private String workoutType;
    private String sportType;
    private int targetSteps;
    private int targetCalories;
    
    public FitnessData() {
        super(); // Initialize entries and totalCalories from ListTrackingData
        this.steps = 0;
        this.caloriesBurned = 0;
        this.workoutType = "";
        this.sportType = "";
        this.targetSteps = 10000;
        this.targetCalories = 500;
    }
    
    // Accessors
    public int getSteps() {
        return steps;
    }
    
    public int getCaloriesBurned() {
        return caloriesBurned;
    }
    
    public String getWorkoutType() {
        return workoutType;
    }
    
    public String getSportType() {
        return sportType;
    }
    
    public int getTargetSteps() {
        return targetSteps;
    }
    
    public int getTargetCalories() {
        return targetCalories;
    }
    
    public ArrayList<String> getActivities() {
        return entries; // Use entries from parent class
    }
    
    // Mutators
    public void setSteps(int steps) {
        this.steps = steps;
    }
    
    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }
    
    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }
    
    public void setSportType(String sportType) {
        this.sportType = sportType;
    }
    
    public void setTargetSteps(int targetSteps) {
        this.targetSteps = targetSteps;
    }
    
    public void setTargetCalories(int targetCalories) {
        this.targetCalories = targetCalories;
    }
    
    public void addActivity(String activity, double calories) {
        addEntry(activity, (int) Math.round(calories));
    }
    
    @Override
    public void addEntry(String entry, int calories) {
        String activityEntry = entry + " - " + String.format("%.2f", (double) calories) + " kcal";
        this.entries.add(activityEntry);
        this.totalCalories += calories;
        this.caloriesBurned += calories;
    }
    
    public void removeActivity(int index) {
        removeEntry(index);
    }
    
    @Override
    public void removeEntry(int index) {
        if (index >= 0 && index < entries.size()) {
            String activityEntry = entries.get(index);
            int calories = parseCaloriesFromEntry(activityEntry);
            this.totalCalories = Math.max(0, this.totalCalories - calories);
            this.caloriesBurned = Math.max(0, this.caloriesBurned - calories);
            entries.remove(index);
        }
    }
    
    @Override
    public void reset() {
        super.reset(); // Clear entries and totalCalories
        this.steps = 0;
        this.caloriesBurned = 0;
        this.workoutType = "";
        this.sportType = "";
    }
    
    @Override
    public int getCurrentValue() {
        return caloriesBurned;
    }
    
    @Override
    public int getTargetValue() {
        return targetCalories;
    }
}

