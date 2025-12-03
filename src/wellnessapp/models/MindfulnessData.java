package wellnessapp.models;

public class MindfulnessData extends TrackingData {
    private int meditationTime; 
    private int targetMeditationTime;
    private String mood;
    
    public MindfulnessData() {
        this.meditationTime = 0;
        this.targetMeditationTime = 10;
        this.mood = ""; 
    }
    
    public int getMeditationTime() {
        return meditationTime;
    }
    
    public int getTargetMeditationTime() {
        return targetMeditationTime;
    }
    
    public String getMood() {
        return mood;
    }
    
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

