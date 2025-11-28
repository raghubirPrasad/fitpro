package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.models.FitnessData;
import wellnessapp.utils.FileHandler;
import wellnessapp.utils.Validator;
import wellnessapp.utils.AnimatedButton;
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
    private float alpha = 0.0f; // For fade-in effect
    
    public FitnessPanel(User user) {
        this.user = user;
        this.fileHandler = FileHandler.getInstance();
        loadData();
        
        setLayout(new BorderLayout());
        setOpaque(true);
        
        // Start fade-in animation
        startFadeInAnimation();
        
        // Title with subtle shadow
        JLabel titleLabel = new JLabel("Fitness Tracking") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                g2d.setColor(new Color(0, 0, 0, 30));
                FontMetrics fm = g2d.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), textX + 2, textY + 2);
                
                g2d.setColor(getForeground());
                g2d.drawString(getText(), textX, textY);
                
                g2d.dispose();
            }
        };
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
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
        gbc.gridwidth = 2;
        mainPanel.add(new JSeparator(), gbc);
        
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
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
        
        // Buttons with animations
        JPanel buttonPanel = new JPanel(new FlowLayout());
        AnimatedButton addButton = new AnimatedButton("Add Data");
        AnimatedButton updateTargetsButton = new AnimatedButton("Update Targets");
        AnimatedButton newDayButton = new AnimatedButton("New Day");
        newDayButton.setButtonColors(new Color(255, 152, 0), new Color(255, 193, 7), new Color(255, 143, 0));
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
    
    private void startFadeInAnimation() {
        alpha = 0.0f;
        Timer fadeTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 0.05f;
                if (alpha >= 1.0f) {
                    alpha = 1.0f;
                    ((Timer) e.getSource()).stop();
                }
                repaint();
            }
        });
        fadeTimer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paintComponent(g2d);
        g2d.dispose();
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
            
            // Visual feedback with animation
            animateSuccessFeedback();
            
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
    
    private void animateSuccessFeedback() {
        // Flash effect on current stats labels
        Timer flashTimer = new Timer(50, new ActionListener() {
            private int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 3) {
                    Color originalColor = currentStepsLabel.getForeground();
                    currentStepsLabel.setForeground(count % 2 == 0 ? new Color(76, 175, 80) : originalColor);
                    currentCaloriesLabel.setForeground(count % 2 == 0 ? new Color(76, 175, 80) : originalColor);
                    count++;
                } else {
                    currentStepsLabel.setForeground(Color.BLACK);
                    currentCaloriesLabel.setForeground(Color.BLACK);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        flashTimer.start();
    }
    
    private void updateDisplay() {
        // Animate number changes
        animateNumberChange(currentStepsLabel, "Steps: ", fitnessData.getSteps());
        animateNumberChange(currentCaloriesLabel, "Calories Burned: ", fitnessData.getCaloriesBurned());
    }
    
    private void animateNumberChange(JLabel label, String prefix, int targetValue) {
        String currentText = label.getText();
        int initialValue = 0;
        try {
            String valueStr = currentText.substring(prefix.length()).trim();
            initialValue = Integer.parseInt(valueStr);
        } catch (Exception e) {
            initialValue = 0;
        }
        
        final int startValue = initialValue;
        
        if (startValue == targetValue) {
            label.setText(prefix + targetValue);
            return;
        }
        
        Timer animTimer = new Timer(10, new ActionListener() {
            private int animatedValue = startValue;
            @Override
            public void actionPerformed(ActionEvent e) {
                int diff = targetValue - animatedValue;
                if (Math.abs(diff) > 0) {
                    animatedValue += diff > 0 ? Math.max(1, diff / 10) : Math.min(-1, diff / 10);
                    if (Math.abs(targetValue - animatedValue) < 1) {
                        animatedValue = targetValue;
                    }
                    label.setText(prefix + animatedValue);
                } else {
                    label.setText(prefix + targetValue);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        animTimer.start();
    }
    
    private void loadData() {
        fitnessData = fileHandler.loadFitnessData().get(user.getUsername());
        if (fitnessData == null) {
            fitnessData = new FitnessData();
        }
    }
}

