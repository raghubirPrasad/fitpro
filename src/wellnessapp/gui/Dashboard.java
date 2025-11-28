package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.models.FitnessData;
import wellnessapp.models.MealData;
import wellnessapp.utils.FileHandler;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard panel showing key metrics
 * Demonstrates: GUI components, layout managers
 */
public class Dashboard extends JPanel {
    private JLabel stepsLabel;
    private JLabel caloriesBurnedLabel;
    private JLabel waterIntakeLabel;
    private JLabel caloriesEatenLabel;
    private User user;
    private FileHandler fileHandler;
    
    public Dashboard(User user) {
        this.user = user;
        this.fileHandler = FileHandler.getInstance();
        setLayout(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        // Metrics panel
        JPanel metricsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        metricsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        stepsLabel = new JLabel("Steps: 0 / 10000", JLabel.CENTER);
        stepsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        metricsPanel.add(new JLabel("Steps Taken:", JLabel.CENTER));
        metricsPanel.add(stepsLabel);
        
        caloriesBurnedLabel = new JLabel("Calories Burned: 0 / 500", JLabel.CENTER);
        caloriesBurnedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        metricsPanel.add(new JLabel("Calories Burned:", JLabel.CENTER));
        metricsPanel.add(caloriesBurnedLabel);
        
        waterIntakeLabel = new JLabel("Water Intake: 0 ml / 2000 ml", JLabel.CENTER);
        waterIntakeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        metricsPanel.add(new JLabel("Water Intake:", JLabel.CENTER));
        metricsPanel.add(waterIntakeLabel);
        
        caloriesEatenLabel = new JLabel("Calories Eaten: 0 / 2000", JLabel.CENTER);
        caloriesEatenLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        metricsPanel.add(new JLabel("Calories Eaten:", JLabel.CENTER));
        metricsPanel.add(caloriesEatenLabel);
        
        add(metricsPanel, BorderLayout.CENTER);
        
        updateMetrics();
    }
    
    public void updateMetrics() {
        FitnessData fitnessData = fileHandler.loadFitnessData().get(user.getUsername());
        MealData mealData = fileHandler.loadMealData().get(user.getUsername());
        
        if (fitnessData == null) {
            fitnessData = new wellnessapp.models.FitnessData();
        }
        if (mealData == null) {
            mealData = new wellnessapp.models.MealData();
        }
        
        stepsLabel.setText("Steps: " + fitnessData.getSteps() + " / " + fitnessData.getTargetSteps());
        caloriesBurnedLabel.setText("Calories Burned: " + fitnessData.getCaloriesBurned() + 
            " / " + fitnessData.getTargetCalories());
        waterIntakeLabel.setText("Water Intake: " + mealData.getWaterIntake() + 
            " ml / " + mealData.getTargetWater() + " ml");
        caloriesEatenLabel.setText("Calories Eaten: " + mealData.getCaloriesEaten() + 
            " / " + mealData.getTargetCalories());
    }
}

