package wellnessapp.models;

import java.io.Serializable;

/**
 * MealItem class to represent a meal item with name, serving size, and calories
 * Demonstrates: Classes, constructors, accessors
 */
public class MealItem implements Serializable {
    private String name;
    private String servingSize;
    private int calories;
    
    public MealItem(String name, String servingSize, int calories) {
        this.name = name;
        this.servingSize = servingSize;
        this.calories = calories;
    }
    
    // Accessors
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

