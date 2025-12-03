package wellnessapp.models;

import java.util.ArrayList;

/**
 * ListTrackingData - Abstract base class for tracking data with list-based entries
 * Demonstrates: Abstract classes, inheritance hierarchy, code reuse
 * 
 * Extends TrackingData to provide:
 * - List-based entry management
 * - Common calorie parsing logic
 * - Entry addition and removal
 */
public abstract class ListTrackingData extends TrackingData {
    protected ArrayList<String> entries;
    protected int totalCalories; // Total calories from entries
    
    /**
     * Constructor for ListTrackingData
     */
    public ListTrackingData() {
        this.entries = new ArrayList<>();
        this.totalCalories = 0;
    }
    
    /**
     * Gets the list of entries
     * @return ArrayList of entry strings
     */
    public ArrayList<String> getEntries() {
        return entries;
    }
    
    /**
     * Adds an entry to the list
     * Must be implemented by subclasses
     * @param entry Entry string
     * @param calories Calories associated with the entry
     */
    public abstract void addEntry(String entry, int calories);
    
    /**
     * Removes an entry from the list at the specified index
     * Must be implemented by subclasses
     * @param index Index of entry to remove
     */
    public abstract void removeEntry(int index);
    
    /**
     * Parses calories from an entry string
     * Common format: "Entry Name - 123.45 kcal" or "Entry Name - 123 kcal"
     * @param entry Entry string to parse
     * @return Parsed calories, or 0 if parsing fails
     */
    protected int parseCaloriesFromEntry(String entry) {
        try {
            // Parse calories from string like "Activity - 123.45 kcal" or "Meal - 123 kcal"
            int kcalIndex = entry.lastIndexOf(" - ");
            if (kcalIndex > 0) {
                String caloriesStr = entry.substring(kcalIndex + 3).replace(" kcal", "").trim();
                // Handle both integer and double values
                if (caloriesStr.contains(".")) {
                    double calories = Double.parseDouble(caloriesStr);
                    return (int) Math.round(calories);
                } else {
                    return Integer.parseInt(caloriesStr);
                }
            }
        } catch (Exception e) {
            // If parsing fails, return 0
        }
        return 0;
    }
    
    /**
     * Gets total calories from entries
     * @return Total calories
     */
    public int getTotalCalories() {
        return totalCalories;
    }
    
    /**
     * Resets the list and total calories
     */
    @Override
    public void reset() {
        entries.clear();
        totalCalories = 0;
    }
}

