package wellnessapp.models;

import java.util.ArrayList;

public abstract class ListTrackingData extends TrackingData {
    protected ArrayList<String> entries;
    protected int totalCalories; 
    public ListTrackingData() {
        this.entries = new ArrayList<>();
        this.totalCalories = 0;
    }

    public ArrayList<String> getEntries() {
        return entries;
    }
    
    public abstract void addEntry(String entry, int calories);

    public abstract void removeEntry(int index);

    protected int parseCaloriesFromEntry(String entry) {
        try {
            int kcalIndex = entry.lastIndexOf(" - ");
            if (kcalIndex > 0) {
                String caloriesStr = entry.substring(kcalIndex + 3).replace(" kcal", "").trim();
                if (caloriesStr.contains(".")) {
                    double calories = Double.parseDouble(caloriesStr);
                    return (int) Math.round(calories);
                } else {
                    return Integer.parseInt(caloriesStr);
                }
            }
        } catch (Exception e) {
        }
        return 0;
    }
    public int getTotalCalories() {
        return totalCalories;
    }

    @Override
    public void reset() {
        entries.clear();
        totalCalories = 0;
    }
}

