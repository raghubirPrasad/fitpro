package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.models.FitnessData;
import wellnessapp.utils.FileHandler;
import wellnessapp.utils.Validator;
import wellnessapp.exceptions.InvalidInputException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * FitnessPanel for tracking fitness activities
 * Demonstrates: GUI components, event handling, exception handling
 */
public class FitnessPanel extends JPanel {
    private User user;
    private FileHandler fileHandler;
    private FitnessData fitnessData;
    
    private JTextField stepsField;
    private JTextField caloriesField;
    private JTextField workoutTypeField;
    private JTextField sportTypeField;
    private JTextField targetStepsField;
    private JTextField targetCaloriesField;
    private JLabel currentStepsLabel;
    private JLabel currentCaloriesLabel;
    
    public FitnessPanel(User user) {
        this.user = user;
        this.fileHandler = FileHandler.getInstance();
        loadData();
        
        setLayout(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("Fitness Tracking", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Current stats
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(new JLabel("Current Stats:"), gbc);
        
        gbc.gridy = 1;
        currentStepsLabel = new JLabel("Steps: " + fitnessData.getSteps());
        mainPanel.add(currentStepsLabel, gbc);
        
        gbc.gridy = 2;
        currentCaloriesLabel = new JLabel("Calories Burned: " + fitnessData.getCaloriesBurned());
        mainPanel.add(currentCaloriesLabel, gbc);
        
        // Separator
        gbc.gridy = 3;
        mainPanel.add(new JSeparator(), gbc);
        
        // Input fields
        gbc.gridwidth = 1;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Add Steps:"), gbc);
        gbc.gridx = 1;
        stepsField = new JTextField(10);
        mainPanel.add(stepsField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(new JLabel("Add Calories:"), gbc);
        gbc.gridx = 1;
        caloriesField = new JTextField(10);
        mainPanel.add(caloriesField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(new JLabel("Workout Type:"), gbc);
        gbc.gridx = 1;
        workoutTypeField = new JTextField(10);
        mainPanel.add(workoutTypeField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(new JLabel("Sport Type:"), gbc);
        gbc.gridx = 1;
        sportTypeField = new JTextField(10);
        mainPanel.add(sportTypeField, gbc);
        
        // Targets
        gbc.gridy = 8;
        mainPanel.add(new JSeparator(), gbc);
        
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Target Steps:"), gbc);
        gbc.gridx = 1;
        targetStepsField = new JTextField(10);
        targetStepsField.setText(String.valueOf(fitnessData.getTargetSteps()));
        mainPanel.add(targetStepsField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 10;
        mainPanel.add(new JLabel("Target Calories:"), gbc);
        gbc.gridx = 1;
        targetCaloriesField = new JTextField(10);
        targetCaloriesField.setText(String.valueOf(fitnessData.getTargetCalories()));
        mainPanel.add(targetCaloriesField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Data");
        JButton updateTargetsButton = new JButton("Update Targets");
        JButton newDayButton = new JButton("New Day");
        buttonPanel.add(addButton);
        buttonPanel.add(updateTargetsButton);
        buttonPanel.add(newDayButton);
        
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Event handlers
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddData();
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
    
    private void handleAddData() {
        try {
            String stepsStr = stepsField.getText().trim();
            String caloriesStr = caloriesField.getText().trim();
            
            if (!stepsStr.isEmpty()) {
                int steps = Validator.parsePositiveInteger(stepsStr, "Steps");
                fitnessData.setSteps(fitnessData.getSteps() + steps);
            }
            
            if (!caloriesStr.isEmpty()) {
                int calories = Validator.parsePositiveInteger(caloriesStr, "Calories");
                fitnessData.setCaloriesBurned(fitnessData.getCaloriesBurned() + calories);
            }
            
            if (!workoutTypeField.getText().trim().isEmpty()) {
                fitnessData.setWorkoutType(workoutTypeField.getText().trim());
            }
            
            if (!sportTypeField.getText().trim().isEmpty()) {
                fitnessData.setSportType(sportTypeField.getText().trim());
            }
            
            fileHandler.saveFitnessData(user.getUsername(), fitnessData);
            updateDisplay();
            
            stepsField.setText("");
            caloriesField.setText("");
            workoutTypeField.setText("");
            sportTypeField.setText("");
            
            JOptionPane.showMessageDialog(this, "Data added successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidInputException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleUpdateTargets() {
        try {
            String targetStepsStr = targetStepsField.getText().trim();
            String targetCaloriesStr = targetCaloriesField.getText().trim();
            
            if (!targetStepsStr.isEmpty()) {
                int targetSteps = Validator.parsePositiveInteger(targetStepsStr, "Target Steps");
                fitnessData.setTargetSteps(targetSteps);
            }
            
            if (!targetCaloriesStr.isEmpty()) {
                int targetCalories = Validator.parsePositiveInteger(targetCaloriesStr, "Target Calories");
                fitnessData.setTargetCalories(targetCalories);
            }
            
            fileHandler.saveFitnessData(user.getUsername(), fitnessData);
            JOptionPane.showMessageDialog(this, "Targets updated successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidInputException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleNewDay() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Reset all fitness data for a new day?", "Confirm", 
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            fitnessData.reset();
            fileHandler.saveFitnessData(user.getUsername(), fitnessData);
            updateDisplay();
            JOptionPane.showMessageDialog(this, "Data reset for new day!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void updateDisplay() {
        currentStepsLabel.setText("Steps: " + fitnessData.getSteps());
        currentCaloriesLabel.setText("Calories Burned: " + fitnessData.getCaloriesBurned());
    }
    
    private void loadData() {
        fitnessData = fileHandler.loadFitnessData().get(user.getUsername());
        if (fitnessData == null) {
            fitnessData = new FitnessData();
        }
    }
}

