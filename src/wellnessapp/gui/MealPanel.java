package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.models.MealData;
import wellnessapp.utils.FileHandler;
import wellnessapp.utils.Validator;
import wellnessapp.utils.AnimatedButton;
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
    private float alpha = 0.0f; // For fade-in effect
    
    public MealPanel(User user) {
        this.user = user;
        this.fileHandler = FileHandler.getInstance();
        loadData();
        
        setLayout(new BorderLayout());
        setOpaque(true);
        
        // Start fade-in animation
        startFadeInAnimation();
        
        // Title with subtle shadow
        JLabel titleLabel = new JLabel("Meal Tracking") {
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
        gbc.gridwidth = 2;
        inputPanel.add(new JSeparator(), gbc);
        
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Target Water (ml):"), gbc);
        gbc.gridx = 1;
        targetWaterField = new JTextField(15);
        targetWaterField.setText(String.valueOf(mealData.getTargetWater()));
        inputPanel.add(targetWaterField, gbc);
        
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Target Calories:"), gbc);
        gbc.gridx = 1;
        targetCaloriesField = new JTextField(15);
        targetCaloriesField.setText(String.valueOf(mealData.getTargetCalories()));
        inputPanel.add(targetCaloriesField, gbc);
        
        // Buttons with animations
        JPanel buttonPanel = new JPanel(new FlowLayout());
        AnimatedButton addMealButton = new AnimatedButton("Add Meal");
        AnimatedButton addWaterButton = new AnimatedButton("Add Water");
        addWaterButton.setButtonColors(new Color(33, 150, 243), new Color(25, 118, 210), new Color(21, 101, 192));
        AnimatedButton updateTargetsButton = new AnimatedButton("Update Targets");
        AnimatedButton newDayButton = new AnimatedButton("New Day");
        newDayButton.setButtonColors(new Color(255, 152, 0), new Color(255, 193, 7), new Color(255, 143, 0));
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
            
            // Visual feedback
            animateMealAdded();
            
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
    
    private void animateMealAdded() {
        // Flash effect and scroll to bottom
        Timer flashTimer = new Timer(50, new ActionListener() {
            private int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 3) {
                    Color originalColor = currentCaloriesLabel.getForeground();
                    currentCaloriesLabel.setForeground(count % 2 == 0 ? new Color(76, 175, 80) : originalColor);
                    count++;
                } else {
                    currentCaloriesLabel.setForeground(Color.BLACK);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        flashTimer.start();
        
        // Scroll to bottom of meals list
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mealsListArea.setCaretPosition(mealsListArea.getDocument().getLength());
            }
        });
    }
    
    private void updateDisplay() {
        animateNumberChange(currentWaterLabel, "Water: ", mealData.getWaterIntake(), " ml");
        animateNumberChange(currentCaloriesLabel, "Calories: ", mealData.getCaloriesEaten(), "");
        updateMealsList();
    }
    
    private void animateNumberChange(JLabel label, String prefix, int targetValue, String suffix) {
        String currentText = label.getText();
        int initialValue = 0;
        try {
            String valueStr = currentText.replace(prefix, "").replace(suffix, "").trim();
            if (valueStr.contains(" ")) {
                valueStr = valueStr.split(" ")[0];
            }
            initialValue = Integer.parseInt(valueStr);
        } catch (Exception e) {
            initialValue = 0;
        }
        
        final int startValue = initialValue;
        
        if (startValue == targetValue) {
            label.setText(prefix + targetValue + suffix);
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
                    label.setText(prefix + animatedValue + suffix);
                } else {
                    label.setText(prefix + targetValue + suffix);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        animTimer.start();
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

