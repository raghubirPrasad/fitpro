package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.models.MealData;
import wellnessapp.utils.FileHandler;
import wellnessapp.utils.Validator;
import wellnessapp.exceptions.InvalidInputException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * MealPanel for tracking meals and water intake
 * Demonstrates: GUI components, event handling, ArrayList usage
 */
public class MealPanel extends JPanel {
    private User user;
    private FileHandler fileHandler;
    private MealData mealData;
    
    private JTextField mealNameField;
    private JTextField mealCaloriesField;
    private JTextField waterField;
    private JTextField targetWaterField;
    private JTextField targetCaloriesField;
    private JTextArea mealsListArea;
    private JLabel currentWaterLabel;
    private JLabel currentCaloriesLabel;
    
    public MealPanel(User user) {
        this.user = user;
        this.fileHandler = FileHandler.getInstance();
        loadData();
        
        setLayout(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("Meal Tracking", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        // Main panel with two columns
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // Left panel - Input
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Current stats
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        inputPanel.add(new JLabel("Current Stats:"), gbc);
        
        gbc.gridy = 1;
        currentWaterLabel = new JLabel("Water: " + mealData.getWaterIntake() + " ml");
        inputPanel.add(currentWaterLabel, gbc);
        
        gbc.gridy = 2;
        currentCaloriesLabel = new JLabel("Calories: " + mealData.getCaloriesEaten());
        inputPanel.add(currentCaloriesLabel, gbc);
        
        gbc.gridy = 3;
        inputPanel.add(new JSeparator(), gbc);
        
        // Meal input
        gbc.gridwidth = 1;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Meal Name:"), gbc);
        gbc.gridx = 1;
        mealNameField = new JTextField(15);
        inputPanel.add(mealNameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(new JLabel("Calories:"), gbc);
        gbc.gridx = 1;
        mealCaloriesField = new JTextField(15);
        inputPanel.add(mealCaloriesField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(new JLabel("Water (ml):"), gbc);
        gbc.gridx = 1;
        waterField = new JTextField(15);
        inputPanel.add(waterField, gbc);
        
        // Targets
        gbc.gridy = 7;
        inputPanel.add(new JSeparator(), gbc);
        
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        inputPanel.add(new JLabel("Target Water (ml):"), gbc);
        gbc.gridx = 1;
        targetWaterField = new JTextField(15);
        targetWaterField.setText(String.valueOf(mealData.getTargetWater()));
        inputPanel.add(targetWaterField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 9;
        inputPanel.add(new JLabel("Target Calories:"), gbc);
        gbc.gridx = 1;
        targetCaloriesField = new JTextField(15);
        targetCaloriesField.setText(String.valueOf(mealData.getTargetCalories()));
        inputPanel.add(targetCaloriesField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addMealButton = new JButton("Add Meal");
        JButton addWaterButton = new JButton("Add Water");
        JButton updateTargetsButton = new JButton("Update Targets");
        JButton newDayButton = new JButton("New Day");
        buttonPanel.add(addMealButton);
        buttonPanel.add(addWaterButton);
        buttonPanel.add(updateTargetsButton);
        buttonPanel.add(newDayButton);
        
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);
        
        // Right panel - Meals list
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JLabel("Meals Today:"), BorderLayout.NORTH);
        mealsListArea = new JTextArea(15, 20);
        mealsListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(mealsListArea);
        listPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(inputPanel);
        mainPanel.add(listPanel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        updateMealsList();
        
        // Event handlers
        addMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddMeal();
            }
        });
        
        addWaterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddWater();
            }
        });
        
        updateTargetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateTargets();
            }
        });
        
        newDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNewDay();
            }
        });
    }
    
    private void handleAddMeal() {
        try {
            String mealName = mealNameField.getText().trim();
            String caloriesStr = mealCaloriesField.getText().trim();
            
            if (mealName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter meal name", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (caloriesStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter calories", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int calories = Validator.parsePositiveInteger(caloriesStr, "Calories");
            mealData.addMeal(mealName, calories);
            fileHandler.saveMealData(user.getUsername(), mealData);
            
            updateDisplay();
            mealNameField.setText("");
            mealCaloriesField.setText("");
            
            JOptionPane.showMessageDialog(this, "Meal added successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidInputException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleAddWater() {
        try {
            String waterStr = waterField.getText().trim();
            if (waterStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter water amount", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int water = Validator.parsePositiveInteger(waterStr, "Water");
            mealData.setWaterIntake(mealData.getWaterIntake() + water);
            fileHandler.saveMealData(user.getUsername(), mealData);
            
            updateDisplay();
            waterField.setText("");
            
            JOptionPane.showMessageDialog(this, "Water added successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidInputException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleUpdateTargets() {
        try {
            String targetWaterStr = targetWaterField.getText().trim();
            String targetCaloriesStr = targetCaloriesField.getText().trim();
            
            if (!targetWaterStr.isEmpty()) {
                int targetWater = Validator.parsePositiveInteger(targetWaterStr, "Target Water");
                mealData.setTargetWater(targetWater);
            }
            
            if (!targetCaloriesStr.isEmpty()) {
                int targetCalories = Validator.parsePositiveInteger(targetCaloriesStr, "Target Calories");
                mealData.setTargetCalories(targetCalories);
            }
            
            fileHandler.saveMealData(user.getUsername(), mealData);
            JOptionPane.showMessageDialog(this, "Targets updated successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidInputException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleNewDay() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Reset all meal data for a new day?", "Confirm", 
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            mealData.reset();
            fileHandler.saveMealData(user.getUsername(), mealData);
            updateDisplay();
            JOptionPane.showMessageDialog(this, "Data reset for new day!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void updateDisplay() {
        currentWaterLabel.setText("Water: " + mealData.getWaterIntake() + " ml");
        currentCaloriesLabel.setText("Calories: " + mealData.getCaloriesEaten());
        updateMealsList();
    }
    
    private void updateMealsList() {
        mealsListArea.setText("");
        for (String meal : mealData.getMeals()) {
            mealsListArea.append(meal + "\n");
        }
    }
    
    private void loadData() {
        mealData = fileHandler.loadMealData().get(user.getUsername());
        if (mealData == null) {
            mealData = new MealData();
        }
    }
}

