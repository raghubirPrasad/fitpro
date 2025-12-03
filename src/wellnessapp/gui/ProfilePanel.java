package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.models.FitnessData;
import wellnessapp.models.MealData;
import wellnessapp.models.MindfulnessData;
import wellnessapp.utils.FileHandler;
import wellnessapp.utils.Calculator;
import wellnessapp.utils.Validator;
import wellnessapp.utils.AnimatedButton;
import wellnessapp.exceptions.InvalidInputException;
import wellnessapp.exceptions.NegativeValueException;
import wellnessapp.exceptions.DecimalValueException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ProfilePanel for displaying and editing user profile and targets
 * Demonstrates: GUI components, data display, form editing, abstract class inheritance
 */
public class ProfilePanel extends BasePanel {
    private FitnessData fitnessData;
    private MealData mealData;
    private MindfulnessData mindfulnessData;
    
    private JLabel nameLabel;
    private JLabel ageLabel;
    private JLabel genderLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel bmiLabel;
    private JLabel bmrLabel;
    private JLabel tdeeLabel;
    
    private JTextField targetCaloriesBurnedField;
    private JTextField targetStepsField;
    private JTextField targetMealCaloriesField;
    private JTextField targetWaterField;
    private JTextField targetMeditationTimeField;
    
    public ProfilePanel(User user) {
        super(user); // Initialize BasePanel (sets user, fileHandler, layout, fade-in)
        loadData();
        
        // Title with subtle shadow
        JLabel titleLabel = new JLabel("Profile") {
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
        
        // Main panel with scroll
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Personal Information Section
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel personalInfoLabel = new JLabel("Personal Information:");
        personalInfoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(personalInfoLabel, gbc);
        
        // Name
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameLabel = new JLabel(user.getName());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(nameLabel, gbc);
        
        // Age
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        ageLabel = new JLabel(String.valueOf(user.getAge()));
        ageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(ageLabel, gbc);
        
        // Gender
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        genderLabel = new JLabel(user.getGender());
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(genderLabel, gbc);
        
        // Username
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameLabel = new JLabel(user.getUsername());
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(usernameLabel, gbc);
        
        // Password (masked)
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordLabel = new JLabel("••••••••");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(passwordLabel, gbc);
        
        // Separator
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        mainPanel.add(new JSeparator(), gbc);
        
        // Health Metrics Section
        gbc.gridy = 7;
        JLabel healthMetricsLabel = new JLabel("Health Metrics:");
        healthMetricsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(healthMetricsLabel, gbc);
        
        // Calculate BMI
        double bmi = Calculator.calculateBMI(user.getWeight(), user.getHeight());
        String bmiCategory = getBMICategory(bmi);
        
        // BMI
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("BMI:"), gbc);
        gbc.gridx = 1;
        bmiLabel = new JLabel(String.format("%.2f (%s)", bmi, bmiCategory));
        bmiLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        bmiLabel.setForeground(getBMIColor(bmiCategory));
        mainPanel.add(bmiLabel, gbc);
        
        // Calculate BMR
        boolean isMale = "Male".equalsIgnoreCase(user.getGender());
        double bmr = Calculator.calculateBMR(user.getWeight(), user.getHeight(), user.getAge(), isMale);
        
        // BMR
        gbc.gridx = 0;
        gbc.gridy = 9;
        mainPanel.add(new JLabel("BMR:"), gbc);
        gbc.gridx = 1;
        bmrLabel = new JLabel(String.format("%.2f calories/day", bmr));
        bmrLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(bmrLabel, gbc);
        
        // Calculate TDEE (using moderate activity level as default: 1.55)
        double tdee = Calculator.calculateTDEE(bmr, 1.55);
        
        // TDEE
        gbc.gridx = 0;
        gbc.gridy = 10;
        mainPanel.add(new JLabel("TDEE (Moderate Activity):"), gbc);
        gbc.gridx = 1;
        tdeeLabel = new JLabel(String.format("%.2f calories/day", tdee));
        tdeeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(tdeeLabel, gbc);
        
        // Separator
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        mainPanel.add(new JSeparator(), gbc);
        
        // Targets Section
        gbc.gridy = 12;
        JLabel targetsLabel = new JLabel("Targets (Editable):");
        targetsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(targetsLabel, gbc);
        
        // Target Calories Burned
        gbc.gridy = 13;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Target Calories Burned:"), gbc);
        gbc.gridx = 1;
        targetCaloriesBurnedField = new JTextField(15);
        targetCaloriesBurnedField.setText(String.valueOf(fitnessData.getTargetCalories()));
        mainPanel.add(targetCaloriesBurnedField, gbc);
        
        // Target Steps
        gbc.gridx = 0;
        gbc.gridy = 14;
        mainPanel.add(new JLabel("Target Steps:"), gbc);
        gbc.gridx = 1;
        targetStepsField = new JTextField(15);
        targetStepsField.setText(String.valueOf(fitnessData.getTargetSteps()));
        mainPanel.add(targetStepsField, gbc);
        
        // Target Meal Calories
        gbc.gridx = 0;
        gbc.gridy = 15;
        mainPanel.add(new JLabel("Target Meal Calories:"), gbc);
        gbc.gridx = 1;
        targetMealCaloriesField = new JTextField(15);
        targetMealCaloriesField.setText(String.valueOf(mealData.getTargetCalories()));
        mainPanel.add(targetMealCaloriesField, gbc);
        
        // Target Water
        gbc.gridx = 0;
        gbc.gridy = 16;
        mainPanel.add(new JLabel("Target Water (ml):"), gbc);
        gbc.gridx = 1;
        targetWaterField = new JTextField(15);
        targetWaterField.setText(String.valueOf(mealData.getTargetWater()));
        mainPanel.add(targetWaterField, gbc);
        
        // Target Meditation Time
        gbc.gridx = 0;
        gbc.gridy = 17;
        mainPanel.add(new JLabel("Target Meditation Time (minutes):"), gbc);
        gbc.gridx = 1;
        targetMeditationTimeField = new JTextField(15);
        targetMeditationTimeField.setText(String.valueOf(mindfulnessData.getTargetMeditationTime()));
        mainPanel.add(targetMeditationTimeField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        AnimatedButton updateButton = new AnimatedButton("Update Targets");
        AnimatedButton logoutButton = new AnimatedButton("Logout");
        logoutButton.setButtonColors(new Color(244, 67, 54), new Color(211, 47, 47), new Color(198, 40, 40));
        buttonPanel.add(updateButton);
        buttonPanel.add(logoutButton);
        
        gbc.gridx = 0;
        gbc.gridy = 18;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);
        
        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
        
        // Event handlers
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateTargets();
            }
        });
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });
    }
    
    @Override
    protected void loadData() {
        fitnessData = fileHandler.loadFitnessData().get(user.getUsername());
        if (fitnessData == null) {
            fitnessData = new FitnessData();
        }
        
        mealData = fileHandler.loadMealData().get(user.getUsername());
        if (mealData == null) {
            mealData = new MealData();
        }
        
        mindfulnessData = fileHandler.loadMindfulnessData().get(user.getUsername());
        if (mindfulnessData == null) {
            mindfulnessData = new MindfulnessData();
        }
    }
    
    private void handleUpdateTargets() {
        try {
            String targetCaloriesBurnedStr = targetCaloriesBurnedField.getText().trim();
            String targetStepsStr = targetStepsField.getText().trim();
            String targetMealCaloriesStr = targetMealCaloriesField.getText().trim();
            String targetWaterStr = targetWaterField.getText().trim();
            String targetMeditationTimeStr = targetMeditationTimeField.getText().trim();
            
            if (!targetCaloriesBurnedStr.isEmpty()) {
                int targetCaloriesBurned = Validator.parsePositiveInteger(targetCaloriesBurnedStr, "Target Calories Burned");
                fitnessData.setTargetCalories(targetCaloriesBurned);
            }
            
            if (!targetStepsStr.isEmpty()) {
                int targetSteps = Validator.parsePositiveInteger(targetStepsStr, "Target Steps");
                fitnessData.setTargetSteps(targetSteps);
            }
            
            if (!targetMealCaloriesStr.isEmpty()) {
                int targetMealCalories = Validator.parsePositiveInteger(targetMealCaloriesStr, "Target Meal Calories");
                mealData.setTargetCalories(targetMealCalories);
            }
            
            if (!targetWaterStr.isEmpty()) {
                int targetWater = Validator.parsePositiveInteger(targetWaterStr, "Target Water");
                mealData.setTargetWater(targetWater);
            }
            
            if (!targetMeditationTimeStr.isEmpty()) {
                int targetMeditationTime = Validator.parsePositiveInteger(targetMeditationTimeStr, "Target Meditation Time");
                mindfulnessData.setTargetMeditationTime(targetMeditationTime);
            }
            
            fileHandler.saveFitnessData(user.getUsername(), fitnessData);
            fileHandler.saveMealData(user.getUsername(), mealData);
            fileHandler.saveMindfulnessData(user.getUsername(), mindfulnessData);
            
            JOptionPane.showMessageDialog(this, "Targets updated successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidInputException | NegativeValueException | DecimalValueException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", "Confirm Logout", 
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Get the parent frame and dispose it
            Window parent = SwingUtilities.getWindowAncestor(this);
            if (parent != null) {
                parent.dispose();
            }
            new LoginFrame().setVisible(true);
        }
    }
    
    private String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }
    
    private Color getBMIColor(String category) {
        switch (category) {
            case "Underweight":
                return new Color(33, 150, 243); // Blue
            case "Normal":
                return new Color(76, 175, 80); // Green
            case "Overweight":
                return new Color(255, 193, 7); // Yellow/Orange
            case "Obese":
                return new Color(244, 67, 54); // Red
            default:
                return Color.BLACK;
        }
    }
    
    @Override
    protected void updateDisplay() {
        // ProfilePanel uses refreshData() method instead
        refreshData();
    }
    
    // Method to refresh data when switching to this panel
    public void refreshData() {
        loadData();
        if (targetCaloriesBurnedField != null) {
            targetCaloriesBurnedField.setText(String.valueOf(fitnessData.getTargetCalories()));
        }
        if (targetStepsField != null) {
            targetStepsField.setText(String.valueOf(fitnessData.getTargetSteps()));
        }
        if (targetMealCaloriesField != null) {
            targetMealCaloriesField.setText(String.valueOf(mealData.getTargetCalories()));
        }
        if (targetWaterField != null) {
            targetWaterField.setText(String.valueOf(mealData.getTargetWater()));
        }
        if (targetMeditationTimeField != null) {
            targetMeditationTimeField.setText(String.valueOf(mindfulnessData.getTargetMeditationTime()));
        }
    }
}

