package wellnessapp.models;

import java.io.Serializable;

/**
 * TrackingData - Abstract base class for data models that track daily activities
 * Demonstrates: Abstract classes, inheritance, polymorphism
 * 
 * Provides common contract for tracking data:
 * - Reset functionality
 * - Progress calculation
 * - Target tracking
 */
public abstract class TrackingData implements Serializable {
    
    /**
     * Resets all daily tracking data
     * Must be implemented by subclasses
     */
    public abstract void reset();
    
    /**
     * Gets the current value being tracked
     * Must be implemented by subclasses
     * @return Current tracked value
     */
    public abstract int getCurrentValue();
    
    /**
     * Gets the target value
     * Must be implemented by subclasses
     * @return Target value
     */
    public abstract int getTargetValue();
    
    /**
     * Calculates progress percentage towards target
     * @return Progress percentage (0-100)
     */
    public double getProgressPercentage() {
        int target = getTargetValue();
        if (target == 0) {
            return 0.0;
        }
        int current = getCurrentValue();
        double percentage = (current * 100.0) / target;
        return Math.min(100.0, Math.max(0.0, percentage)); // Clamp between 0 and 100
    }
    
    /**
     * Checks if target has been reached
     * @return true if current value >= target value
     */
    public boolean isTargetReached() {
        return getCurrentValue() >= getTargetValue();
    }
}

