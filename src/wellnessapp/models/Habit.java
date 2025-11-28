package wellnessapp.models;

import java.io.Serializable;

/**
 * Habit class representing a single habit
 * Demonstrates: Classes, constructors, accessors, mutators, Comparable interface
 */
public class Habit implements Serializable, Comparable<Habit> {
    private String name;
    private boolean completed;
    
    public Habit(String name) {
        this.name = name;
        this.completed = false;
    }
    
    // Accessors
    public String getName() {
        return name;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    // Mutators
    public void setName(String name) {
        this.name = name;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public void toggle() {
        this.completed = !this.completed;
    }
    
    // Comparable interface implementation
    @Override
    public int compareTo(Habit other) {
        return this.name.compareTo(other.name);
    }
    
    @Override
    public String toString() {
        return name + (completed ? " [✓]" : " [ ]");
    }
}

