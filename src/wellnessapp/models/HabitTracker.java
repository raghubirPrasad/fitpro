package wellnessapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * HabitTracker class to manage multiple habits
 * Demonstrates: ArrayList, Iterator, classes, constructors
 */
public class HabitTracker implements Serializable {
    private ArrayList<Habit> habits;
    
    public HabitTracker() {
        this.habits = new ArrayList<Habit>();
    }
    
    public void addHabit(String name) {
        habits.add(new Habit(name));
    }
    
    public void removeHabit(String name) {
        Iterator<Habit> iterator = habits.iterator();
        while (iterator.hasNext()) {
            Habit habit = iterator.next();
            if (habit.getName().equals(name)) {
                iterator.remove();
                break;
            }
        }
    }
    
    public void toggleHabit(String name) {
        for (Habit habit : habits) {
            if (habit.getName().equals(name)) {
                habit.toggle();
                break;
            }
        }
    }
    
    public ArrayList<Habit> getHabits() {
        return habits;
    }
    
    public void reset() {
        for (Habit habit : habits) {
            habit.setCompleted(false);
        }
    }
    
    public int getCompletedCount() {
        int count = 0;
        for (Habit habit : habits) {
            if (habit.isCompleted()) {
                count++;
            }
        }
        return count;
    }
}

