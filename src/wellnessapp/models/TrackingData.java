package wellnessapp.models;

import java.io.Serializable;

public abstract class TrackingData implements Serializable {

    public abstract void reset();
    public abstract int getCurrentValue();
    public abstract int getTargetValue();
    public double getProgressPercentage() {
        int target = getTargetValue();
        if (target == 0) {
            return 0.0;
        }
        int current = getCurrentValue();
        double percentage = (current * 100.0) / target;
        return Math.min(100.0, Math.max(0.0, percentage));
    }

    public boolean isTargetReached() {
        return getCurrentValue() >= getTargetValue();
    }
}

