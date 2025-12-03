package wellnessapp.models;

/**
 * MindfulnessData class to track meditation time
 * Demonstrates: Classes, constructors, accessors, mutators, abstract class inheritance
 */
public class MindfulnessData extends TrackingData {
    private int meditationTime; // in minutes
    private int targetMeditationTime; // in minutes
    private String mood; // Current mood
    
    public MindfulnessData() {
        this.meditationTime = 0;
        this.targetMeditationTime = 10; // Default 10 minutes
        this.mood = ""; // No mood selected initially
    }
    
    // Accessors
    public int getMeditationTime() {
        return meditationTime;
    }
    
    public int getTargetMeditationTime() {
        return targetMeditationTime;
    }
    
    public String getMood() {
        return mood;
    }
    
    // Mutators
    public void setMeditationTime(int meditationTime) {
        this.meditationTime = meditationTime;
    }
    
    public void setTargetMeditationTime(int targetMeditationTime) {
        this.targetMeditationTime = targetMeditationTime;
    }
    
    public void addMeditationTime(int minutes) {
        this.meditationTime += minutes;
    }
    
    public void setMood(String mood) {
        this.mood = mood;
    }
    
    @Override
    public void reset() {
        this.meditationTime = 0;
        this.mood = "";
    }
    
    @Override
    public int getCurrentValue() {
        return meditationTime;
    }
    
    @Override
    public int getTargetValue() {
        return targetMeditationTime;
    }
}

