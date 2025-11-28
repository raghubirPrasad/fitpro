package wellnessapp.models;

import java.io.Serializable;

/**
 * FitnessData class to track fitness activities
 * Demonstrates: Classes, constructors, accessors, mutators
 */
public class FitnessData implements Serializable {
    private int steps;
    private int caloriesBurned;
    private String workoutType;
    private String sportType;
    private int targetSteps;
    private int targetCalories;
    
    public FitnessData() {
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
    
    public void reset() {
        this.steps = 0;
        this.caloriesBurned = 0;
        this.workoutType = "";
        this.sportType = "";
    }
}

