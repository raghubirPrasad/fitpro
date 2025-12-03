package wellnessapp.utils;

import wellnessapp.models.User;
import wellnessapp.models.FitnessData;
import wellnessapp.models.MealData;
import wellnessapp.models.HabitTracker;
import wellnessapp.models.MindfulnessData;

import java.util.HashMap;
import java.util.Map;
public class FileHandler {
    private static FileHandler instance;
    
    private Map<String, User> users;
    private Map<String, FitnessData> fitnessData;
    private Map<String, MealData> mealData;
    private Map<String, HabitTracker> habitTrackers;
    private Map<String, MindfulnessData> mindfulnessData;
    
    private FileHandler() {
        users = new HashMap<String, User>();
        fitnessData = new HashMap<String, FitnessData>();
        mealData = new HashMap<String, MealData>();
        habitTrackers = new HashMap<String, HabitTracker>();
        mindfulnessData = new HashMap<String, MindfulnessData>();
    }
    
    public static FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }
        return instance;
    }
    
    public void saveUsers(Map<String, User> users) {
        this.users = users;
    }
    
    public Map<String, User> loadUsers() {
        return users;
    }
    
    public void saveFitnessData(String username, FitnessData data) {
        fitnessData.put(username, data);
    }
    
    public Map<String, FitnessData> loadFitnessData() {
        return fitnessData;
    }
    
    public void saveMealData(String username, MealData data) {
        mealData.put(username, data);
    }
    
    public Map<String, MealData> loadMealData() {
        return mealData;
    }
    
    public void saveHabitTracker(String username, HabitTracker tracker) {
        habitTrackers.put(username, tracker);
    }
    
    public Map<String, HabitTracker> loadHabitTrackers() {
        return habitTrackers;
    }
    
    public void saveMindfulnessData(String username, MindfulnessData data) {
        mindfulnessData.put(username, data);
    }
    
    public Map<String, MindfulnessData> loadMindfulnessData() {
        return mindfulnessData;
    }
}
