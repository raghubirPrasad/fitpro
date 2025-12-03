package wellnessapp.models;

import java.util.ArrayList;


public class FitnessData extends ListTrackingData {
    private int steps;
    private int caloriesBurned;
    private String workoutType;
    private String sportType;
    private int targetSteps;
    private int targetCalories;
    
    public FitnessData() {
        super(); 
        this.steps = 0;
        this.caloriesBurned = 0;
        this.workoutType = "";
        this.sportType = "";
        this.targetSteps = 10000;
        this.targetCalories = 500;
    }
    
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
        return entries;
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
        super.reset();
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

