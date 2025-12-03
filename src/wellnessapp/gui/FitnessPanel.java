package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.models.FitnessData;
import wellnessapp.utils.FileHandler;
import wellnessapp.utils.Validator;
import wellnessapp.utils.AnimatedButton;
import wellnessapp.exceptions.InvalidInputException;
import wellnessapp.exceptions.NegativeValueException;
import wellnessapp.exceptions.DecimalValueException;
import wellnessapp.exceptions.SpecialCharacterException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * FitnessPanel for tracking fitness activities
 * Demonstrates: GUI components, event handling, exception handling, abstract class inheritance
 */
public class FitnessPanel extends BasePanel {
    private FitnessData fitnessData;
    
    // Steps and targets section
    private JTextField stepsField;
    private JTextField targetStepsField;
    private JTextField targetCaloriesField;
    private JLabel currentStepsLabel;
    private JLabel currentCaloriesLabel;
    
    // Workout/Sport section
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> activityComboBox;
    private JTextField repsField;
    private JTextField timeField;
    private JTextField distanceField;
    private JTextField speedField;
    private JTextField manualCaloriesField;
    private JTextField manualActivityField;
    private JLabel caloriesResultLabel;
    private JList<String> activitiesList;
    private DefaultListModel<String> activitiesListModel;
    
    // Labels for dynamic fields
    private JLabel repsLabel;
    private JLabel timeLabel;
    private JLabel distanceLabel;
    private JLabel speedLabel;
    private JLabel manualCaloriesLabel;
    private JLabel manualActivityLabel;
    
    // MET values for workouts
    private static final Map<String, Double> WORKOUT_MET = new HashMap<>();
    static {
        WORKOUT_MET.put("Push-ups", 8.0);
        WORKOUT_MET.put("Pull-ups", 8.0);
        WORKOUT_MET.put("Squats", 5.0);
        WORKOUT_MET.put("Jump Squats", 8.0);
        WORKOUT_MET.put("Lunges", 4.5);
        WORKOUT_MET.put("Sit-ups", 8.0);
        WORKOUT_MET.put("Crunches", 3.8);
        WORKOUT_MET.put("Mountain Climbers", 8.0);
        WORKOUT_MET.put("Burpees", 10.0);
        WORKOUT_MET.put("Jumping Jacks", 8.0);
        WORKOUT_MET.put("Dips", 5.0);
        WORKOUT_MET.put("Calf Raises", 3.5);
        WORKOUT_MET.put("Deadlifts Light", 6.0);
        WORKOUT_MET.put("Deadlifts Heavy", 8.0);
        WORKOUT_MET.put("Bench Press", 6.0);
        WORKOUT_MET.put("Shoulder Press", 5.0);
        WORKOUT_MET.put("Bicep Curls", 3.5);
        WORKOUT_MET.put("Tricep Extension", 3.5);
        WORKOUT_MET.put("Dumbbell Rows", 6.0);
        WORKOUT_MET.put("Kettlebell Swings", 9.8);
        WORKOUT_MET.put("Kettlebell Snatch", 10.0);
    }
    
    // MET values for sports
    private static final Map<String, Double> SPORT_MET = new HashMap<>();
    static {
        SPORT_MET.put("Running Slow (6 km/h)", 6.0);
        SPORT_MET.put("Running Moderate (8 km/h)", 8.3);
        SPORT_MET.put("Running Fast (10 km/h)", 10.0);
        SPORT_MET.put("Running Very Fast (12 km/h)", 11.0);
        SPORT_MET.put("Cycling Light", 4.0);
        SPORT_MET.put("Cycling Moderate", 8.0);
        SPORT_MET.put("Cycling Fast", 10.0);
        SPORT_MET.put("Swimming Casual", 6.0);
        SPORT_MET.put("Swimming Fast", 10.0);
        SPORT_MET.put("Football", 7.0);
        SPORT_MET.put("Basketball", 6.5);
        SPORT_MET.put("Tennis Singles", 8.0);
        SPORT_MET.put("Tennis Doubles", 6.0);
        SPORT_MET.put("Badminton", 7.0);
        SPORT_MET.put("Cricket", 5.5);
        SPORT_MET.put("Volleyball", 3.8);
        SPORT_MET.put("Boxing", 12.0);
        SPORT_MET.put("CrossFit", 12.0);
        SPORT_MET.put("Hiking", 6.0);
        SPORT_MET.put("Rowing Moderate", 7.0);
        SPORT_MET.put("Rowing Fast", 12.0);
        SPORT_MET.put("Rope Skipping", 12.3);
        SPORT_MET.put("Elliptical", 5.0);
    }
    
    private double caloriesBurned = 0.0; // Global variable for calories burned
    
    public FitnessPanel(User user) {
        super(user); // Initialize BasePanel (sets user, fileHandler, layout, fade-in)
        loadData();
        
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
        currentStepsLabel = new JLabel("Steps: " + fitnessData.getSteps());
        inputPanel.add(currentStepsLabel, gbc);
        
        gbc.gridy = 2;
        currentCaloriesLabel = new JLabel("Calories Burned: " + fitnessData.getCaloriesBurned() + " kcal");
        inputPanel.add(currentCaloriesLabel, gbc);
        
        // Separator
        gbc.gridy = 3;
        inputPanel.add(new JSeparator(), gbc);
        
        // Steps section
        gbc.gridwidth = 1;
        gbc.gridy = 4;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Add Steps:"), gbc);
        gbc.gridx = 1;
        stepsField = new JTextField(10);
        inputPanel.add(stepsField, gbc);
        
        // Add Steps button
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        AnimatedButton addStepsButton = new AnimatedButton("Add Steps");
        inputPanel.add(addStepsButton, gbc);
        
        // Separator before workout/sport section
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        inputPanel.add(new JSeparator(), gbc);
        
        // Workout/Sport section header
        gbc.gridy = 7;
        inputPanel.add(new JLabel("Workout & Sport Tracking:"), gbc);
        
        // Category dropdown
        gbc.gridwidth = 1;
        gbc.gridy = 8;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryComboBox = new JComboBox<>(new String[]{"Select Category", "Workout", "Sport", "Manual"});
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCategoryChange();
            }
        });
        inputPanel.add(categoryComboBox, gbc);
        
        // Activity dropdown
        gbc.gridy = 9;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Activity:"), gbc);
        gbc.gridx = 1;
        activityComboBox = new JComboBox<>(new String[]{"Select Activity"});
        activityComboBox.setEnabled(false);
        activityComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleActivityChange();
            }
        });
        inputPanel.add(activityComboBox, gbc);
        
        // Reps field (for workouts)
        gbc.gridy = 10;
        gbc.gridx = 0;
        repsLabel = new JLabel("Reps:");
        repsLabel.setVisible(false);
        inputPanel.add(repsLabel, gbc);
        gbc.gridx = 1;
        repsField = new JTextField(10);
        repsField.setVisible(false);
        inputPanel.add(repsField, gbc);
        
        // Time field (for sports)
        gbc.gridy = 11;
        gbc.gridx = 0;
        timeLabel = new JLabel("Time (minutes):");
        timeLabel.setVisible(false);
        inputPanel.add(timeLabel, gbc);
        gbc.gridx = 1;
        timeField = new JTextField(10);
        timeField.setVisible(false);
        inputPanel.add(timeField, gbc);
        
        // Distance field (for running)
        gbc.gridy = 12;
        gbc.gridx = 0;
        distanceLabel = new JLabel("Distance (km):");
        distanceLabel.setVisible(false);
        inputPanel.add(distanceLabel, gbc);
        gbc.gridx = 1;
        distanceField = new JTextField(10);
        distanceField.setVisible(false);
        inputPanel.add(distanceField, gbc);
        
        // Speed field (for running)
        gbc.gridy = 13;
        gbc.gridx = 0;
        speedLabel = new JLabel("Speed (km/h):");
        speedLabel.setVisible(false);
        inputPanel.add(speedLabel, gbc);
        gbc.gridx = 1;
        speedField = new JTextField(10);
        speedField.setVisible(false);
        inputPanel.add(speedField, gbc);
        
        // Manual activity name field (for manual entry)
        gbc.gridy = 14;
        gbc.gridx = 0;
        manualActivityLabel = new JLabel("Activity Name:");
        manualActivityLabel.setVisible(false);
        inputPanel.add(manualActivityLabel, gbc);
        gbc.gridx = 1;
        manualActivityField = new JTextField(10);
        manualActivityField.setVisible(false);
        inputPanel.add(manualActivityField, gbc);
        
        // Manual calories field (for manual entry)
        gbc.gridy = 15;
        gbc.gridx = 0;
        manualCaloriesLabel = new JLabel("Calories:");
        manualCaloriesLabel.setVisible(false);
        inputPanel.add(manualCaloriesLabel, gbc);
        gbc.gridx = 1;
        manualCaloriesField = new JTextField(10);
        manualCaloriesField.setVisible(false);
        inputPanel.add(manualCaloriesField, gbc);
        
        // Calculate and Update Calories button
        gbc.gridy = 16;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        AnimatedButton calculateButton = new AnimatedButton("Calculate and Update Calories");
        inputPanel.add(calculateButton, gbc);
        
        // Calories result label
        gbc.gridy = 17;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        caloriesResultLabel = new JLabel("Calories Burned: 0 kcal");
        caloriesResultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        caloriesResultLabel.setForeground(new Color(76, 175, 80));
        inputPanel.add(caloriesResultLabel, gbc);
        
        // Separator before targets
        gbc.gridy = 18;
        inputPanel.add(new JSeparator(), gbc);
        
        // Targets section
        gbc.gridwidth = 1;
        gbc.gridy = 19;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Target Steps:"), gbc);
        gbc.gridx = 1;
        targetStepsField = new JTextField(10);
        targetStepsField.setText(String.valueOf(fitnessData.getTargetSteps()));
        inputPanel.add(targetStepsField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 20;
        inputPanel.add(new JLabel("Target Calories:"), gbc);
        gbc.gridx = 1;
        targetCaloriesField = new JTextField(10);
        targetCaloriesField.setText(String.valueOf(fitnessData.getTargetCalories()));
        inputPanel.add(targetCaloriesField, gbc);
        
        // Buttons with animations
        JPanel buttonPanel = new JPanel(new FlowLayout());
        AnimatedButton updateTargetsButton = new AnimatedButton("Update Targets");
        buttonPanel.add(updateTargetsButton);
        
        gbc.gridx = 0;
        gbc.gridy = 21;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);
        
        // Right panel - Activities list
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JLabel("Fitness Today:"), BorderLayout.NORTH);
        
        activitiesListModel = new DefaultListModel<>();
        activitiesList = new JList<>(activitiesListModel);
        activitiesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(activitiesList);
        listPanel.add(listScrollPane, BorderLayout.CENTER);
        
        // Remove Selected button
        JPanel buttonListPanel = new JPanel(new FlowLayout());
        AnimatedButton removeSelectedButton = new AnimatedButton("Remove Selected");
        removeSelectedButton.setButtonColors(new Color(244, 67, 54), new Color(211, 47, 47), new Color(198, 40, 40));
        buttonListPanel.add(removeSelectedButton);
        listPanel.add(buttonListPanel, BorderLayout.SOUTH);
        
        mainPanel.add(inputPanel);
        mainPanel.add(listPanel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        updateActivitiesList();
        
        // Event handlers
        addStepsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddSteps();
            }
        });
        
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCalculateCalories();
            }
        });
        
        updateTargetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateTargets();
            }
        });
    
        removeSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRemoveSelected();
            }
        });
    }
    
    private void handleCategoryChange() {
        String category = (String) categoryComboBox.getSelectedItem();
        activityComboBox.removeAllItems();
        
        if ("Workout".equals(category)) {
            activityComboBox.addItem("Select Workout");
            for (String workout : WORKOUT_MET.keySet()) {
                activityComboBox.addItem(workout);
            }
            activityComboBox.setEnabled(true);
            hideAllActivityFields();
        } else if ("Sport".equals(category)) {
            activityComboBox.addItem("Select Sport");
            for (String sport : SPORT_MET.keySet()) {
                activityComboBox.addItem(sport);
            }
            activityComboBox.setEnabled(true);
            hideAllActivityFields();
        } else if ("Manual".equals(category)) {
            activityComboBox.addItem("Manual Entry");
            activityComboBox.setEnabled(false);
            showManualFields();
        } else {
            activityComboBox.addItem("Select Activity");
            activityComboBox.setEnabled(false);
            hideAllActivityFields();
        }
        
        caloriesResultLabel.setText("Calories Burned: 0 kcal");
        caloriesBurned = 0.0;
    }
    
    private void handleActivityChange() {
        String activity = (String) activityComboBox.getSelectedItem();
        if (activity == null || activity.startsWith("Select")) {
            hideAllActivityFields();
            return;
        }
        
        String category = (String) categoryComboBox.getSelectedItem();
        
        if ("Workout".equals(category)) {
            showWorkoutFields();
        } else if ("Sport".equals(category)) {
            if (activity.startsWith("Running")) {
                showRunningFields();
            } else {
                showSportFields();
            }
        }
        
        caloriesResultLabel.setText("Calories Burned: 0 kcal");
        caloriesBurned = 0.0;
    }
    
    private void showWorkoutFields() {
        repsLabel.setVisible(true);
        repsField.setVisible(true);
        timeLabel.setVisible(false);
        timeField.setVisible(false);
        distanceLabel.setVisible(false);
        distanceField.setVisible(false);
        speedLabel.setVisible(false);
        speedField.setVisible(false);
        manualActivityLabel.setVisible(false);
        manualActivityField.setVisible(false);
        manualCaloriesLabel.setVisible(false);
        manualCaloriesField.setVisible(false);
    }
    
    private void showSportFields() {
        repsLabel.setVisible(false);
        repsField.setVisible(false);
        timeLabel.setVisible(true);
        timeField.setVisible(true);
        distanceLabel.setVisible(false);
        distanceField.setVisible(false);
        speedLabel.setVisible(false);
        speedField.setVisible(false);
        manualActivityLabel.setVisible(false);
        manualActivityField.setVisible(false);
        manualCaloriesLabel.setVisible(false);
        manualCaloriesField.setVisible(false);
    }
    
    private void showRunningFields() {
        repsLabel.setVisible(false);
        repsField.setVisible(false);
        timeLabel.setVisible(true);
        timeField.setVisible(true);
        distanceLabel.setVisible(true);
        distanceField.setVisible(true);
        speedLabel.setVisible(true);
        speedField.setVisible(true);
        manualActivityLabel.setVisible(false);
        manualActivityField.setVisible(false);
        manualCaloriesLabel.setVisible(false);
        manualCaloriesField.setVisible(false);
    }
    
    private void showManualFields() {
        repsLabel.setVisible(false);
        repsField.setVisible(false);
        timeLabel.setVisible(false);
        timeField.setVisible(false);
        distanceLabel.setVisible(false);
        distanceField.setVisible(false);
        speedLabel.setVisible(false);
        speedField.setVisible(false);
        manualActivityLabel.setVisible(true);
        manualActivityField.setVisible(true);
        manualCaloriesLabel.setVisible(true);
        manualCaloriesField.setVisible(true);
    }
    
    private void hideAllActivityFields() {
        repsLabel.setVisible(false);
        repsField.setVisible(false);
        timeLabel.setVisible(false);
        timeField.setVisible(false);
        distanceLabel.setVisible(false);
        distanceField.setVisible(false);
        speedLabel.setVisible(false);
        speedField.setVisible(false);
        manualActivityLabel.setVisible(false);
        manualActivityField.setVisible(false);
        manualCaloriesLabel.setVisible(false);
        manualCaloriesField.setVisible(false);
    }
    
    private void handleAddSteps() {
        try {
            String stepsStr = stepsField.getText().trim();
            
            if (stepsStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter steps to add.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int steps = Validator.parseStepsOrAge(stepsStr, "Steps");
            fitnessData.setSteps(fitnessData.getSteps() + steps);
            fileHandler.saveFitnessData(user.getUsername(), fitnessData);
            updateDisplay();
            
            stepsField.setText("");
            animateSuccessFeedback();
            
            JOptionPane.showMessageDialog(this, "Steps added successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidInputException | NegativeValueException | DecimalValueException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleCalculateCalories() {
        try {
            String category = (String) categoryComboBox.getSelectedItem();
            String activity = (String) activityComboBox.getSelectedItem();
            
            if (category == null || category.equals("Select Category")) {
                JOptionPane.showMessageDialog(this, "Please select a category.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Handle Manual category
            if ("Manual".equals(category)) {
                String activityName = manualActivityField.getText().trim();
                if (activityName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter activity name.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Validate activity name (alphabets and numbers only)
                activityName = Validator.validateAlphabetsAndNumbers(activityName, "Activity Name");
                String caloriesStr = manualCaloriesField.getText().trim();
                if (caloriesStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter calories.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                caloriesBurned = Validator.parsePositiveDouble(caloriesStr, "Calories");
                activity = activityName;
            } else {
                // For Workout and Sport categories
                if (activity == null || activity.startsWith("Select")) {
                    JOptionPane.showMessageDialog(this, "Please select an activity.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Use weight from user data
                double weightKg = user.getWeight();
                
                if ("Workout".equals(category)) {
                    caloriesBurned = calculateWorkoutCalories(activity, weightKg);
                    // Get reps value (already validated by calculateWorkoutCalories)
                    String repsStr = repsField.getText().trim();
                    // Format: "Activity Name (X reps)"
                    activity = activity + " (" + repsStr + " reps)";
                } else if ("Sport".equals(category)) {
                    if (activity.startsWith("Running")) {
                        caloriesBurned = calculateRunningCalories(activity, weightKg);
                        // Get time, distance, speed values (already validated by calculateRunningCalories)
                        String timeStr = timeField.getText().trim();
                        String distanceStr = distanceField.getText().trim();
                        String speedStr = speedField.getText().trim();
                        // Format: "Running Type (X min, Y km, Z km/h)"
                        activity = activity + " (" + timeStr + " min, " + distanceStr + " km, " + speedStr + " km/h)";
                    } else {
                        caloriesBurned = calculateSportCalories(activity, weightKg);
                        // Get time value (already validated by calculateSportCalories)
                        String timeStr = timeField.getText().trim();
                        // Format: "Sport Name (X min)"
                        activity = activity + " (" + timeStr + " min)";
                    }
                }
            }
            
            // Update display
            caloriesResultLabel.setText(String.format("Calories Burned: %.2f kcal", caloriesBurned));
            
            // Add activity to list (this also updates caloriesBurned in FitnessData)
            fitnessData.addActivity(activity, caloriesBurned);
            
            // Save fitness data
            fileHandler.saveFitnessData(user.getUsername(), fitnessData);
            updateDisplay();
            
            // Visual feedback
            animateSuccessFeedback();
            
            // Scroll to bottom of activities list
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (activitiesListModel.getSize() > 0) {
                        activitiesList.ensureIndexIsVisible(activitiesListModel.getSize() - 1);
                    }
                }
            });
            
            // Clear input fields
            if ("Manual".equals(category)) {
                manualActivityField.setText("");
                manualCaloriesField.setText("");
            } else if ("Workout".equals(category)) {
                repsField.setText("");
            } else if ("Sport".equals(category)) {
                timeField.setText("");
                distanceField.setText("");
                speedField.setText("");
            }
            
            JOptionPane.showMessageDialog(this, 
                String.format("Calories calculated: %.2f kcal", caloriesBurned), 
                "Success", JOptionPane.INFORMATION_MESSAGE);
                
        } catch (InvalidInputException | NegativeValueException | DecimalValueException | SpecialCharacterException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error calculating calories: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private double calculateWorkoutCalories(String workout, double weightKg) throws InvalidInputException, NegativeValueException, DecimalValueException {
        Double met = WORKOUT_MET.get(workout);
        if (met == null) {
            throw new InvalidInputException("Invalid workout selected.");
        }
        
        String repsStr = repsField.getText().trim();
        if (repsStr.isEmpty()) {
            throw new InvalidInputException("Please enter number of reps.");
        }
        
        int reps = Validator.parsePositiveInteger(repsStr, "Reps");
        
        // Formula: calories = 0.0175 * MET * weightKg * 0.033 * reps
        double calories = 0.0175 * met * weightKg * 0.033 * reps;
        return calories;
    }
    
    private double calculateSportCalories(String sport, double weightKg) throws InvalidInputException, NegativeValueException {
        Double met = SPORT_MET.get(sport);
        if (met == null) {
            throw new InvalidInputException("Invalid sport selected.");
        }
        
        String timeStr = timeField.getText().trim();
        if (timeStr.isEmpty()) {
            throw new InvalidInputException("Please enter time in minutes.");
        }
        
        double time = Validator.parsePositiveDouble(timeStr, "Time");
        
        // Formula: calories = 0.0175 * MET * weightKg * time
        double calories = 0.0175 * met * weightKg * time;
        return calories;
    }
    
    private double calculateRunningCalories(String runningType, double weightKg) throws InvalidInputException, NegativeValueException {
        Double met = SPORT_MET.get(runningType);
        if (met == null) {
            throw new InvalidInputException("Invalid running type selected.");
        }
        
        String timeStr = timeField.getText().trim();
        String distanceStr = distanceField.getText().trim();
        String speedStr = speedField.getText().trim();
        
        if (timeStr.isEmpty() || distanceStr.isEmpty() || speedStr.isEmpty()) {
            throw new InvalidInputException("Please enter time, distance, and speed for running.");
        }
        
        double time = Validator.parsePositiveDouble(timeStr, "Time");
        double distance = Validator.parsePositiveDouble(distanceStr, "Distance");
        double speed = Validator.parsePositiveDouble(speedStr, "Speed");
        
        if (speed <= 0) {
            throw new InvalidInputException("Speed must be greater than 0.");
        }
        
        // Formulas:
        // caloriesPerMin = 0.0175 * MET * weightKg
        // caloriesPerKm = (0.0175 * MET * weightKg * 60) / speed
        // totalCalories = caloriesPerMin * time + caloriesPerKm * distance
        double caloriesPerMin = 0.0175 * met * weightKg;
        double caloriesPerKm = (0.0175 * met * weightKg * 60) / speed;
        double totalCalories = caloriesPerMin * time + caloriesPerKm * distance;
        
        return totalCalories;
    }
    
    private void handleUpdateTargets() {
        try {
            String targetStepsStr = targetStepsField.getText().trim();
            String targetCaloriesStr = targetCaloriesField.getText().trim();
            
            if (targetStepsStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter target steps.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (targetCaloriesStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter target calories.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int targetSteps = Validator.parsePositiveInteger(targetStepsStr, "Target Steps");
            int targetCalories = Validator.parsePositiveInteger(targetCaloriesStr, "Target Calories");
            
            fitnessData.setTargetSteps(targetSteps);
            fitnessData.setTargetCalories(targetCalories);
            
            fileHandler.saveFitnessData(user.getUsername(), fitnessData);
            JOptionPane.showMessageDialog(this, "Targets updated successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidInputException | NegativeValueException | DecimalValueException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleRemoveSelected() {
        int selectedIndex = activitiesList.getSelectedIndex();
        if (selectedIndex >= 0) {
            fitnessData.removeActivity(selectedIndex);
            fileHandler.saveFitnessData(user.getUsername(), fitnessData);
            updateDisplay();
            updateActivitiesList();
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
    
    @Override
    protected void updateDisplay() {
        // Animate number changes
        animateNumberChange(currentStepsLabel, "Steps: ", fitnessData.getSteps());
        animateNumberChange(currentCaloriesLabel, "Calories Burned: ", fitnessData.getCaloriesBurned());
        updateActivitiesList();
    }
    
    private void updateActivitiesList() {
        activitiesListModel.clear();
        for (String activity : fitnessData.getActivities()) {
            activitiesListModel.addElement(activity);
        }
    }
    
    private void animateNumberChange(JLabel label, String prefix, int targetValue) {
        String currentText = label.getText();
        int initialValue = 0;
        try {
            String valueStr = currentText.substring(prefix.length()).trim();
            // Remove " kcal" if present
            if (valueStr.endsWith(" kcal")) {
                valueStr = valueStr.substring(0, valueStr.length() - 5);
            }
            initialValue = Integer.parseInt(valueStr);
        } catch (Exception e) {
            initialValue = 0;
        }
        
        final int startValue = initialValue;
        
        if (startValue == targetValue) {
            if (prefix.contains("Calories")) {
                label.setText(prefix + targetValue + " kcal");
            } else {
            label.setText(prefix + targetValue);
            }
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
                    if (prefix.contains("Calories")) {
                        label.setText(prefix + animatedValue + " kcal");
                    } else {
                    label.setText(prefix + animatedValue);
                    }
                } else {
                    if (prefix.contains("Calories")) {
                        label.setText(prefix + targetValue + " kcal");
                } else {
                    label.setText(prefix + targetValue);
                    }
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        animTimer.start();
    }
    
    @Override
    protected void loadData() {
        fitnessData = fileHandler.loadFitnessData().get(user.getUsername());
        if (fitnessData == null) {
            fitnessData = new FitnessData();
        }
    }
    
    public void refreshData() {
        FitnessData updatedData = fileHandler.loadFitnessData().get(user.getUsername());
        if (updatedData != null) {
            fitnessData = updatedData;
            if (targetStepsField != null) {
                targetStepsField.setText(String.valueOf(fitnessData.getTargetSteps()));
            }
            if (targetCaloriesField != null) {
                targetCaloriesField.setText(String.valueOf(fitnessData.getTargetCalories()));
            }
            updateDisplay();
        }
    }
}
