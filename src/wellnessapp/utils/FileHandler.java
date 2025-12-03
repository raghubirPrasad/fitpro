package wellnessapp.utils;

import wellnessapp.models.User;
import wellnessapp.models.FitnessData;
import wellnessapp.models.MealData;
import wellnessapp.models.HabitTracker;
import wellnessapp.models.MindfulnessData;

import java.util.HashMap;
import java.util.Map;

/**
 * FileHandler class - simplified to use in-memory storage only
 * Data is not persisted between app sessions
 * Demonstrates: Singleton pattern, in-memory data management
 */
public class FileHandler {
    // Singleton pattern for FileHandler
    private static FileHandler instance;
    
    // In-memory storage for session data
    private Map<String, User> users;
    private Map<String, FitnessData> fitnessData;
    private Map<String, MealData> mealData;
    private Map<String, HabitTracker> habitTrackers;
    private Map<String, MindfulnessData> mindfulnessData;
    
    private FileHandler() {
        // Initialize in-memory storage maps
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
    
    // Save users map - stores in memory
    public void saveUsers(Map<String, User> users) {
        this.users = users;
    }
    
    // Load users map - returns in-memory data
    public Map<String, User> loadUsers() {
        return users;
    }
    
    // Save fitness data for a user - stores in memory
    public void saveFitnessData(String username, FitnessData data) {
        fitnessData.put(username, data);
    }
    
    // Load fitness data for all users - returns in-memory data
    public Map<String, FitnessData> loadFitnessData() {
        return fitnessData;
    }
    
    // Save meal data for a user - stores in memory
    public void saveMealData(String username, MealData data) {
        mealData.put(username, data);
    }
    
    // Load meal data for all users - returns in-memory data
    public Map<String, MealData> loadMealData() {
        return mealData;
    }
    
    // Save habit tracker for a user - stores in memory
    public void saveHabitTracker(String username, HabitTracker tracker) {
        habitTrackers.put(username, tracker);
    }
    
    // Load habit trackers for all users - returns in-memory data
    public Map<String, HabitTracker> loadHabitTrackers() {
        return habitTrackers;
    }
    
    // Save mindfulness data for a user - stores in memory
    public void saveMindfulnessData(String username, MindfulnessData data) {
        mindfulnessData.put(username, data);
    }
    
    // Load mindfulness data for all users - returns in-memory data
    public Map<String, MindfulnessData> loadMindfulnessData() {
        return mindfulnessData;
    }
}

