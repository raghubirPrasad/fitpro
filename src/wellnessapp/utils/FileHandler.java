package wellnessapp.utils;

import wellnessapp.models.User;
import wellnessapp.models.FitnessData;
import wellnessapp.models.MealData;
import wellnessapp.models.HabitTracker;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * FileHandler class for data persistence
 * Demonstrates: File handling, serialization, exception handling
 */
public class FileHandler {
    private static final String USERS_FILE = "data/users.dat";
    private static final String FITNESS_FILE = "data/fitness.dat";
    private static final String MEAL_FILE = "data/meals.dat";
    private static final String HABIT_FILE = "data/habits.dat";
    
    // Singleton pattern for FileHandler
    private static FileHandler instance;
    
    private FileHandler() {
        // Create data directory if it doesn't exist
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
    
    public static FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }
        return instance;
    }
    
    // Save users map
    public void saveUsers(Map<String, User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    
    // Load users map
    @SuppressWarnings("unchecked")
    public Map<String, User> loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            return (Map<String, User>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new HashMap<String, User>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading users: " + e.getMessage());
            return new HashMap<String, User>();
        }
    }
    
    // Save fitness data for a user
    public void saveFitnessData(String username, FitnessData data) {
        Map<String, FitnessData> allData = loadFitnessData();
        allData.put(username, data);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FITNESS_FILE))) {
            oos.writeObject(allData);
        } catch (IOException e) {
            System.err.println("Error saving fitness data: " + e.getMessage());
        }
    }
    
    // Load fitness data for all users
    @SuppressWarnings("unchecked")
    public Map<String, FitnessData> loadFitnessData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FITNESS_FILE))) {
            return (Map<String, FitnessData>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new HashMap<String, FitnessData>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading fitness data: " + e.getMessage());
            return new HashMap<String, FitnessData>();
        }
    }
    
    // Save meal data for a user
    public void saveMealData(String username, MealData data) {
        Map<String, MealData> allData = loadMealData();
        allData.put(username, data);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MEAL_FILE))) {
            oos.writeObject(allData);
        } catch (IOException e) {
            System.err.println("Error saving meal data: " + e.getMessage());
        }
    }
    
    // Load meal data for all users
    @SuppressWarnings("unchecked")
    public Map<String, MealData> loadMealData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MEAL_FILE))) {
            return (Map<String, MealData>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new HashMap<String, MealData>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading meal data: " + e.getMessage());
            return new HashMap<String, MealData>();
        }
    }
    
    // Save habit tracker for a user
    public void saveHabitTracker(String username, HabitTracker tracker) {
        Map<String, HabitTracker> allData = loadHabitTrackers();
        allData.put(username, tracker);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HABIT_FILE))) {
            oos.writeObject(allData);
        } catch (IOException e) {
            System.err.println("Error saving habit tracker: " + e.getMessage());
        }
    }
    
    // Load habit trackers for all users
    @SuppressWarnings("unchecked")
    public Map<String, HabitTracker> loadHabitTrackers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(HABIT_FILE))) {
            return (Map<String, HabitTracker>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new HashMap<String, HabitTracker>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading habit trackers: " + e.getMessage());
            return new HashMap<String, HabitTracker>();
        }
    }
}

