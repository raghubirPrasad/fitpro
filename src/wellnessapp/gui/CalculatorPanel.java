package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.utils.Calculator;
import wellnessapp.utils.Validator;
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
    
    private JTextField ageField;
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    private JLabel bmiLabel;
    private JLabel bmrLabel;
    private JLabel tdeeLabel;
    
    public CalculatorPanel(User user) {
        this.user = user;
        setLayout(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("Health Calculators", JLabel.CENTER);
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
        mainPanel.add(new JLabel("Height: " + user.getHeight() + " cm"), gbc);
        gbc.gridx = 1;
        mainPanel.add(new JLabel("Weight: " + user.getWeight() + " kg"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        bmiLabel = new JLabel("BMI: " + String.format("%.2f", 
            Calculator.calculateBMI(user.getWeight(), user.getHeight())));
        bmiLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(bmiLabel, gbc);
        
        // Separator
        gbc.gridy = 3;
        mainPanel.add(new JSeparator(), gbc);
        
        // BMR section
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(new JLabel("BMR Calculator:"), gbc);
        
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        ageField = new JTextField(10);
        mainPanel.add(ageField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
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
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        JButton calculateButton = new JButton("Calculate BMR & TDEE");
        mainPanel.add(calculateButton, gbc);
        
        gbc.gridy = 8;
        bmrLabel = new JLabel("BMR: -");
        bmrLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(bmrLabel, gbc);
        
        gbc.gridy = 9;
        tdeeLabel = new JLabel("TDEE: -");
        tdeeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(tdeeLabel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Event handler
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCalculate();
            }
        });
    }
    
    private void handleCalculate() {
        try {
            String ageStr = ageField.getText().trim();
            if (ageStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter age", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int age = Validator.parsePositiveInteger(ageStr, "Age");
            boolean isMale = maleRadio.isSelected();
            
            double bmr = Calculator.calculateBMR(user.getWeight(), user.getHeight(), age, isMale);
            double tdeeSedentary = Calculator.calculateTDEE(bmr, 1.2);
            double tdeeModerate = Calculator.calculateTDEE(bmr, 1.55);
            double tdeeActive = Calculator.calculateTDEE(bmr, 1.725);
            
            bmrLabel.setText("BMR: " + String.format("%.2f", bmr) + " calories/day");
            tdeeLabel.setText("<html>TDEE:<br>" +
                "Sedentary: " + String.format("%.2f", tdeeSedentary) + " cal/day<br>" +
                "Moderate: " + String.format("%.2f", tdeeModerate) + " cal/day<br>" +
                "Active: " + String.format("%.2f", tdeeActive) + " cal/day</html>");
        } catch (InvalidInputException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

