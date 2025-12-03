package wellnessapp.models;

import java.io.Serializable;

public class MealItem implements Serializable {
    private String name;
    private String servingSize;
    private int calories;
    
    public MealItem(String name, String servingSize, int calories) {
        this.name = name;
        this.servingSize = servingSize;
        this.calories = calories;
    }
    
    public String getName() {
        return name;
    }
    
    public String getServingSize() {
        return servingSize;
    }
    
    public int getCalories() {
        return calories;
    }
    
    @Override
    public String toString() {
        return name + " (" + servingSize + ") - " + calories + " kcal";
    }
}

