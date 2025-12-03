package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.models.MealData;
import wellnessapp.utils.Calculator;
import wellnessapp.utils.Validator;
import wellnessapp.utils.FileHandler;
import wellnessapp.utils.AnimatedButton;
import wellnessapp.exceptions.InvalidInputException;
import wellnessapp.exceptions.NegativeValueException;
import wellnessapp.exceptions.DecimalValueException;
import wellnessapp.exceptions.ZeroValueException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CalculatorPanel for health metric calculations
 * Demonstrates: GUI components, static methods, utility classes, abstract class inheritance
 */
public class CalculatorPanel extends BasePanel {
    
    private JTextField weightField;
    private JTextField heightField;
    private JTextField ageField;
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    private JLabel bmiLabel;
    private JLabel bmrLabel;
    private JLabel tdeeLabel;
    private AnimatedButton sedentaryButton;
    private AnimatedButton moderateButton;
    private AnimatedButton activeButton;
    
    // Animation variables
    private Timer bmiAnimationTimer;
    private Timer bmrAnimationTimer;
    private double animatedBMI = 0.0;
    private double animatedBMR = 0.0;
    private double animatedTDEE = 0.0;
    
    public CalculatorPanel(User user) {
        super(user); // Initialize BasePanel (sets user, fileHandler, layout, fade-in)
        loadData();
        
        // Title with subtle shadow
        JLabel titleLabel = new JLabel("Health Calculators") {
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
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // BMI section
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(new JLabel("BMI Calculator:"), gbc);
        
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Weight (kg):"), gbc);
        gbc.gridx = 1;
        weightField = new JTextField(15);
        weightField.setPreferredSize(new Dimension(150, 25));
        weightField.setText(String.valueOf(user.getWeight()));
        mainPanel.add(weightField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Height (cm):"), gbc);
        gbc.gridx = 1;
        heightField = new JTextField(15);
        heightField.setPreferredSize(new Dimension(150, 25));
        heightField.setText(String.valueOf(user.getHeight()));
        mainPanel.add(heightField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton calculateBMIButton = new JButton("Calculate BMI");
        mainPanel.add(calculateBMIButton, gbc);
        
        gbc.gridy = 4;
        bmiLabel = new JLabel("BMI: -");
        bmiLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(bmiLabel, gbc);
        
        // Separator
        gbc.gridy = 5;
        mainPanel.add(new JSeparator(), gbc);
        
        // BMR section
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        mainPanel.add(new JLabel("BMR Calculator:"), gbc);
        
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        ageField = new JTextField(15);
        ageField.setPreferredSize(new Dimension(150, 25));
        ageField.setText(String.valueOf(user.getAge()));
        mainPanel.add(ageField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        mainPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        JPanel genderPanel = new JPanel(new FlowLayout());
        // Pre-select gender based on user profile
        String userGender = user.getGender();
        boolean isMaleDefault = "Male".equalsIgnoreCase(userGender);
        boolean isFemaleDefault = "Female".equalsIgnoreCase(userGender);
        maleRadio = new JRadioButton("Male", isMaleDefault);
        femaleRadio = new JRadioButton("Female", isFemaleDefault);
        // If gender is "Other" or unknown, default to Male
        if (!isMaleDefault && !isFemaleDefault) {
            maleRadio.setSelected(true);
        }
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        mainPanel.add(genderPanel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        JButton calculateButton = new JButton("Calculate BMR");
        mainPanel.add(calculateButton, gbc);
        
        gbc.gridy = 10;
        bmrLabel = new JLabel("BMR: -");
        bmrLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(bmrLabel, gbc);
        
        // TDEE Activity Level Buttons
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        mainPanel.add(new JLabel("Set Daily Calorie Target (TDEE):"), gbc);
        
        gbc.gridy = 12;
        gbc.gridwidth = 1;
        JPanel tdeeButtonPanel = new JPanel(new FlowLayout());
        sedentaryButton = new AnimatedButton("Sedentary");
        sedentaryButton.setButtonColors(new Color(158, 158, 158), new Color(97, 97, 97), new Color(66, 66, 66));
        moderateButton = new AnimatedButton("Moderate");
        moderateButton.setButtonColors(new Color(255, 193, 7), new Color(255, 160, 0), new Color(255, 143, 0));
        activeButton = new AnimatedButton("Active");
        activeButton.setButtonColors(new Color(76, 175, 80), new Color(56, 142, 60), new Color(46, 125, 50));
        tdeeButtonPanel.add(sedentaryButton);
        tdeeButtonPanel.add(moderateButton);
        tdeeButtonPanel.add(activeButton);
        mainPanel.add(tdeeButtonPanel, gbc);
        
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        tdeeLabel = new JLabel("TDEE: -");
        tdeeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(tdeeLabel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        calculateBMIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCalculateBMI();
            }
        });
        
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCalculateBMR();
            }
        });
        
        sedentaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSetTDEE(1.2, "Sedentary");
            }
        });
        
        moderateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSetTDEE(1.55, "Moderate");
            }
        });
        
        activeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSetTDEE(1.725, "Active");
            }
        });
    }
    
    @Override
    protected void loadData() {
        // CalculatorPanel doesn't load specific data, just uses user data
        // Weight, height, age are already in User object
    }
    
    @Override
    protected void updateDisplay() {
        // CalculatorPanel doesn't have a persistent display to update
        // Results are calculated on demand
    }
    
    private void handleCalculateBMI() {
        try {
            String weightStr = weightField.getText().trim();
            String heightStr = heightField.getText().trim();
            
            if (weightStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter weight", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (heightStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter height", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double weight = Validator.parsePositiveDoubleNoZero(weightStr, "Weight");
            double height = Validator.parsePositiveDoubleNoZero(heightStr, "Height");
            
            double bmi = Calculator.calculateBMI(weight, height);
            animateBMI(bmi);
        } catch (InvalidInputException | NegativeValueException | ZeroValueException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void animateBMI(double targetBMI) {
        if (bmiAnimationTimer != null && bmiAnimationTimer.isRunning()) {
            bmiAnimationTimer.stop();
        }
        
        animatedBMI = 0.0;
        bmiAnimationTimer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double diff = targetBMI - animatedBMI;
                if (Math.abs(diff) > 0.01) {
                    animatedBMI += diff / 20.0;
                    if (Math.abs(targetBMI - animatedBMI) < 0.01) {
                        animatedBMI = targetBMI;
                    }
                    String category = getBMICategory(animatedBMI);
                    bmiLabel.setText("BMI: " + String.format("%.2f", animatedBMI) + " (" + category + ")");
                    updateBMIColor(category);
                } else {
                    animatedBMI = targetBMI;
                    String category = getBMICategory(animatedBMI);
                    bmiLabel.setText("BMI: " + String.format("%.2f", animatedBMI) + " (" + category + ")");
                    updateBMIColor(category);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        bmiAnimationTimer.start();
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
    
    private void updateBMIColor(String category) {
        Color categoryColor;
        switch (category) {
            case "Underweight":
                categoryColor = new Color(33, 150, 243); // Blue
                break;
            case "Normal":
                categoryColor = new Color(76, 175, 80); // Green
                break;
            case "Overweight":
                categoryColor = new Color(255, 193, 7); // Yellow/Orange
                break;
            case "Obese":
                categoryColor = new Color(244, 67, 54); // Red
                break;
            default:
                categoryColor = Color.BLACK;
        }
        bmiLabel.setForeground(categoryColor);
    }
    
    private void handleCalculateBMR() {
        try {
            String weightStr = weightField.getText().trim();
            String heightStr = heightField.getText().trim();
            String ageStr = ageField.getText().trim();
            
            if (weightStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter weight", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (heightStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter height", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (ageStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter age", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double weight = Validator.parsePositiveDoubleNoZero(weightStr, "Weight");
            double height = Validator.parsePositiveDoubleNoZero(heightStr, "Height");
            int age = Validator.parsePositiveInteger(ageStr, "Age");
            boolean isMale = maleRadio.isSelected();
            
            double bmr = Calculator.calculateBMR(weight, height, age, isMale);
            animateBMR(bmr);
        } catch (InvalidInputException | NegativeValueException | DecimalValueException | ZeroValueException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleSetTDEE(double activityLevel, String activityName) {
        try {
            String weightStr = weightField.getText().trim();
            String heightStr = heightField.getText().trim();
            String ageStr = ageField.getText().trim();
            
            if (weightStr.isEmpty() || heightStr.isEmpty() || ageStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please calculate BMR first by entering weight, height, and age", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double weight = Validator.parsePositiveDoubleNoZero(weightStr, "Weight");
            double height = Validator.parsePositiveDoubleNoZero(heightStr, "Height");
            int age = Validator.parseAge(ageStr, "Age");
            boolean isMale = maleRadio.isSelected();
            
            // Calculate TDEE
            double tdee = Calculator.calculateTDEE(weight, height, age, isMale, activityLevel);
            
            // Animate TDEE display
            animateTDEE(tdee, activityName);
            
            // Update target calories in MealData
            MealData mealData = fileHandler.loadMealData().get(user.getUsername());
            if (mealData == null) {
                mealData = new MealData();
            }
            
            int targetCalories = (int) Math.round(tdee);
            mealData.setTargetCalories(targetCalories);
            fileHandler.saveMealData(user.getUsername(), mealData);
            
            // Show confirmation
            JOptionPane.showMessageDialog(this, 
                "Target calories set to " + targetCalories + " cal/day (" + activityName + " activity level)", 
                "Target Updated", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidInputException | NegativeValueException | DecimalValueException | ZeroValueException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void animateBMR(double targetBMR) {
        if (bmrAnimationTimer != null && bmrAnimationTimer.isRunning()) {
            bmrAnimationTimer.stop();
        }
        
        animatedBMR = 0.0;
        bmrAnimationTimer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double bmrDiff = targetBMR - animatedBMR;
                if (Math.abs(bmrDiff) > 0.1) {
                    animatedBMR += bmrDiff / 20.0;
                    if (Math.abs(targetBMR - animatedBMR) < 0.1) {
                        animatedBMR = targetBMR;
                    }
                    bmrLabel.setText("BMR: " + String.format("%.2f", animatedBMR) + " calories/day");
                } else {
                    animatedBMR = targetBMR;
                    bmrLabel.setText("BMR: " + String.format("%.2f", animatedBMR) + " calories/day");
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        bmrAnimationTimer.start();
    }
    
    private void animateTDEE(double targetTDEE, String activityName) {
        animatedTDEE = 0.0;
        Timer tdeeTimer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double tdeeDiff = targetTDEE - animatedTDEE;
                if (Math.abs(tdeeDiff) > 0.1) {
                    animatedTDEE += tdeeDiff / 20.0;
                    if (Math.abs(targetTDEE - animatedTDEE) < 0.1) {
                        animatedTDEE = targetTDEE;
                    }
                    tdeeLabel.setText("TDEE (" + activityName + "): " + String.format("%.2f", animatedTDEE) + " cal/day");
                } else {
                    animatedTDEE = targetTDEE;
                    int targetCal = (int) Math.round(targetTDEE);
                    tdeeLabel.setText("<html>TDEE (" + activityName + "): " + String.format("%.2f", animatedTDEE) + 
                        " cal/day<br><b>Target calories set to: " + targetCal + " cal/day</b></html>");
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        tdeeTimer.start();
    }
}
