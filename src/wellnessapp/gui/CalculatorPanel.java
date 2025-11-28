package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.models.MealData;
import wellnessapp.utils.Calculator;
import wellnessapp.utils.Validator;
import wellnessapp.utils.FileHandler;
import wellnessapp.utils.AnimatedButton;
import wellnessapp.exceptions.InvalidInputException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CalculatorPanel for health metric calculations
 * Demonstrates: GUI components, static methods, utility classes
 */
public class CalculatorPanel extends JPanel {
    private User user;
    private FileHandler fileHandler;
    
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
    private float alpha = 0.0f; // For fade-in effect
    private Timer bmiAnimationTimer;
    private Timer bmrAnimationTimer;
    private double animatedBMI = 0.0;
    private double animatedBMR = 0.0;
    private double animatedTDEE = 0.0;
    
    public CalculatorPanel(User user) {
        this.user = user;
        this.fileHandler = FileHandler.getInstance();
        setLayout(new BorderLayout());
        setOpaque(true);
        
        // Start fade-in animation
        startFadeInAnimation();
        
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
        weightField = new JTextField(10);
        weightField.setText(String.valueOf(user.getWeight()));
        mainPanel.add(weightField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Height (cm):"), gbc);
        gbc.gridx = 1;
        heightField = new JTextField(10);
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
        ageField = new JTextField(10);
        mainPanel.add(ageField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        mainPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        JPanel genderPanel = new JPanel(new FlowLayout());
        maleRadio = new JRadioButton("Male", true);
        femaleRadio = new JRadioButton("Female");
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
        
        // Event handlers
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
        
        // TDEE button handlers
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
            
            double weight = Validator.parsePositiveDouble(weightStr, "Weight");
            double height = Validator.parsePositiveDouble(heightStr, "Height");
            
            double bmi = Calculator.calculateBMI(weight, height);
            animateBMI(bmi);
        } catch (InvalidInputException e) {
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
                    bmiLabel.setText("BMI: " + String.format("%.2f", animatedBMI));
                } else {
                    animatedBMI = targetBMI;
                    bmiLabel.setText("BMI: " + String.format("%.2f", animatedBMI));
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        bmiAnimationTimer.start();
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
            
            double weight = Validator.parsePositiveDouble(weightStr, "Weight");
            double height = Validator.parsePositiveDouble(heightStr, "Height");
            int age = Validator.parsePositiveInteger(ageStr, "Age");
            boolean isMale = maleRadio.isSelected();
            
            double bmr = Calculator.calculateBMR(weight, height, age, isMale);
            animateBMR(bmr);
        } catch (InvalidInputException e) {
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
            
            double weight = Validator.parsePositiveDouble(weightStr, "Weight");
            double height = Validator.parsePositiveDouble(heightStr, "Height");
            int age = Validator.parsePositiveInteger(ageStr, "Age");
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
        } catch (InvalidInputException e) {
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

