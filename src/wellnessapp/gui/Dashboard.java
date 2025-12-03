package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.models.FitnessData;
import wellnessapp.models.MealData;
import wellnessapp.models.HabitTracker;
import wellnessapp.models.MindfulnessData;
import wellnessapp.utils.FileHandler;
import wellnessapp.utils.AnimatedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends BasePanel {
    private JLabel stepsLabel;
    private JLabel caloriesBurnedLabel;
    private JLabel waterIntakeLabel;
    private JLabel caloriesEatenLabel;
    private JLabel habitsCompletedLabel;
    private JLabel mindfulnessLabel;
    private JProgressBar stepsProgressBar;
    private JProgressBar caloriesBurnedProgressBar;
    private JProgressBar waterProgressBar;
    private JProgressBar caloriesEatenProgressBar;
    private JProgressBar habitsCompletedProgressBar;
    private JProgressBar mindfulnessProgressBar;
    
    private int animatedSteps = 0;
    private int animatedCaloriesBurned = 0;
    private int animatedWater = 0;
    private int animatedCaloriesEaten = 0;
    private int animatedHabitsCompleted = 0;
    private int animatedMeditationTime = 0;
    
    private int targetSteps = 10000;
    private int targetCaloriesBurned = 500;
    private int targetWater = 2000;
    private int targetCaloriesEaten = 2000;
    private int targetHabits = 0;
    private int targetMeditationTime = 10;
    
    private Timer animationTimer;
    
    public Dashboard(User user) {
        super(user); // Initialize BasePanel (sets user, fileHandler, layout, fade-in)
        loadData();
        
        JLabel greetingLabel = new JLabel("Hello " + user.getName() + "!") {
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
        greetingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        greetingLabel.setForeground(new Color(33, 150, 243));
        
        JLabel titleLabel = new JLabel("Dashboard") {
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
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        headerPanel.add(greetingLabel, BorderLayout.NORTH);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel stepsNameLabel = new JLabel("Steps Taken:", JLabel.LEFT);
        stepsNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(stepsNameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        Object[] stepsComponents = createMetricDisplay(10000);
        stepsLabel = (JLabel) stepsComponents[0];
        stepsProgressBar = (JProgressBar) stepsComponents[1];
        JPanel stepsValuePanel = (JPanel) stepsComponents[2];
        mainPanel.add(stepsValuePanel, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel caloriesBurnedNameLabel = new JLabel("Calories Burned:", JLabel.LEFT);
        caloriesBurnedNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(caloriesBurnedNameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        Object[] caloriesBurnedComponents = createMetricDisplay(500);
        caloriesBurnedLabel = (JLabel) caloriesBurnedComponents[0];
        caloriesBurnedProgressBar = (JProgressBar) caloriesBurnedComponents[1];
        JPanel caloriesBurnedValuePanel = (JPanel) caloriesBurnedComponents[2];
        mainPanel.add(caloriesBurnedValuePanel, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel waterNameLabel = new JLabel("Water Intake:", JLabel.LEFT);
        waterNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(waterNameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        Object[] waterComponents = createMetricDisplay(2000);
        waterIntakeLabel = (JLabel) waterComponents[0];
        waterProgressBar = (JProgressBar) waterComponents[1];
        JPanel waterValuePanel = (JPanel) waterComponents[2];
        mainPanel.add(waterValuePanel, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel caloriesEatenNameLabel = new JLabel("Calories Eaten:", JLabel.LEFT);
        caloriesEatenNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(caloriesEatenNameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        Object[] caloriesEatenComponents = createMetricDisplay(2000);
        caloriesEatenLabel = (JLabel) caloriesEatenComponents[0];
        caloriesEatenProgressBar = (JProgressBar) caloriesEatenComponents[1];
        JPanel caloriesEatenValuePanel = (JPanel) caloriesEatenComponents[2];
        mainPanel.add(caloriesEatenValuePanel, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel habitsCompletedNameLabel = new JLabel("Habit Progress:", JLabel.LEFT);
        habitsCompletedNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(habitsCompletedNameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        Object[] habitsCompletedComponents = createMetricDisplay(10);
        habitsCompletedLabel = (JLabel) habitsCompletedComponents[0];
        habitsCompletedProgressBar = (JProgressBar) habitsCompletedComponents[1];
        JPanel habitsCompletedValuePanel = (JPanel) habitsCompletedComponents[2];
        mainPanel.add(habitsCompletedValuePanel, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel mindfulnessNameLabel = new JLabel("Mindfulness Meter:", JLabel.LEFT);
        mindfulnessNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(mindfulnessNameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        Object[] mindfulnessComponents = createMetricDisplay(10);
        mindfulnessLabel = (JLabel) mindfulnessComponents[0];
        mindfulnessProgressBar = (JProgressBar) mindfulnessComponents[1];
        JPanel mindfulnessValuePanel = (JPanel) mindfulnessComponents[2];
        mainPanel.add(mindfulnessValuePanel, gbc);
        
        // New Day button
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 10, 10, 10);
        AnimatedButton newDayButton = new AnimatedButton("New Day");
        newDayButton.setButtonColors(new Color(255, 152, 0), new Color(255, 193, 7), new Color(255, 143, 0));
        newDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNewDay();
            }
        });
        mainPanel.add(newDayButton, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        updateMetrics();
    }
    
    private void handleNewDay() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Reset all tracking data for a new day?\n\nThis will reset:\n- Fitness activities and calories\n- Meal entries and water intake\n- Habit completion status\n- Meditation time and mood", 
            "Confirm New Day", 
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Reset FitnessData
            FitnessData fitnessData = fileHandler.loadFitnessData().get(user.getUsername());
            if (fitnessData == null) {
                fitnessData = new FitnessData();
            }
            fitnessData.reset();
            fileHandler.saveFitnessData(user.getUsername(), fitnessData);
            
            // Reset MealData
            MealData mealData = fileHandler.loadMealData().get(user.getUsername());
            if (mealData == null) {
                mealData = new MealData();
            }
            mealData.reset();
            fileHandler.saveMealData(user.getUsername(), mealData);
            
            // Reset HabitTracker
            HabitTracker habitTracker = fileHandler.loadHabitTrackers().get(user.getUsername());
            if (habitTracker == null) {
                habitTracker = new HabitTracker();
            }
            habitTracker.reset();
            fileHandler.saveHabitTracker(user.getUsername(), habitTracker);
            
            // Reset MindfulnessData
            MindfulnessData mindfulnessData = fileHandler.loadMindfulnessData().get(user.getUsername());
            if (mindfulnessData == null) {
                mindfulnessData = new MindfulnessData();
            }
            mindfulnessData.reset();
            fileHandler.saveMindfulnessData(user.getUsername(), mindfulnessData);
            
            // Update dashboard display
            updateMetrics();
            
            JOptionPane.showMessageDialog(this, "All data reset for new day!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private Object[] createMetricDisplay(int maxValue) {
        // Panel to hold value label and progress bar - matches input field alignment
        JPanel panel = new JPanel(new BorderLayout(5, 5)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Subtle background gradient
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(250, 250, 250),
                    0, getHeight(), new Color(245, 245, 245)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
                
                // Subtle border
                g2d.setColor(new Color(220, 220, 220));
                g2d.setStroke(new BasicStroke(1.0f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
                
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        
        // Value label container - left aligned to match text field position
        JPanel labelContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        labelContainer.setOpaque(false);
        JLabel valueLabel = new JLabel("0 / " + maxValue) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                super.paintComponent(g2d);
                g2d.dispose();
            }
        };
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        valueLabel.setHorizontalAlignment(SwingConstants.LEFT);
        labelContainer.add(valueLabel);
        panel.add(labelContainer, BorderLayout.NORTH);
        
        // Progress bar (bottom) - left aligned with subtle effects
        JPanel progressContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        progressContainer.setOpaque(false);
        JProgressBar progressBar = new JProgressBar(0, maxValue) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw background with subtle gradient
                GradientPaint bgGradient = new GradientPaint(
                    0, 0, new Color(245, 245, 245),
                    0, getHeight(), new Color(235, 235, 235)
                );
                g2d.setPaint(bgGradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
                
                // Draw progress with gradient
                if (getValue() > 0) {
                    double percentage = (double) getValue() / getMaximum();
                    int progressWidth = (int) (getWidth() * percentage);
                    
                    GradientPaint progressGradient = new GradientPaint(
                        0, 0, getForeground().brighter(),
                        0, getHeight(), getForeground().darker()
                    );
                    g2d.setPaint(progressGradient);
                    g2d.fillRoundRect(2, 2, progressWidth - 4, getHeight() - 4, 3, 3);
                    
                    // Subtle highlight
                    g2d.setColor(new Color(255, 255, 255, 100));
                    g2d.fillRoundRect(2, 2, progressWidth - 4, (getHeight() - 4) / 2, 3, 3);
                }
                
                // Border
                g2d.setColor(new Color(200, 200, 200));
                g2d.setStroke(new BasicStroke(1.0f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
                
                g2d.dispose();
            }
        };
        progressBar.setValue(0);
        progressBar.setStringPainted(false);
        progressBar.setForeground(new Color(100, 150, 255));
        progressBar.setBackground(new Color(240, 240, 240));
        progressBar.setBorderPainted(false);
        progressBar.setPreferredSize(new Dimension(200, 25));
        progressContainer.add(progressBar);
        panel.add(progressContainer, BorderLayout.CENTER);
        
        // Return array: [label, progressBar, panel]
        return new Object[] {valueLabel, progressBar, panel};
    }
    
    @Override
    protected void loadData() {
        // Dashboard loads data in updateMetrics() method instead
    }
    
    @Override
    protected void updateDisplay() {
        // Dashboard uses updateMetrics() method instead
        updateMetrics();
    }
    
    public void updateMetrics() {
        FitnessData fitnessData = fileHandler.loadFitnessData().get(user.getUsername());
        MealData mealData = fileHandler.loadMealData().get(user.getUsername());
        HabitTracker habitTracker = fileHandler.loadHabitTrackers().get(user.getUsername());
        MindfulnessData mindfulnessData = fileHandler.loadMindfulnessData().get(user.getUsername());
        
        if (fitnessData == null) {
            fitnessData = new wellnessapp.models.FitnessData();
        }
        if (mealData == null) {
            mealData = new wellnessapp.models.MealData();
        }
        if (habitTracker == null) {
            habitTracker = new wellnessapp.models.HabitTracker();
        }
        if (mindfulnessData == null) {
            mindfulnessData = new wellnessapp.models.MindfulnessData();
        }
        
        // Get target values
        targetSteps = fitnessData.getTargetSteps();
        targetCaloriesBurned = fitnessData.getTargetCalories();
        targetWater = mealData.getTargetWater();
        targetCaloriesEaten = mealData.getTargetCalories();
        targetHabits = habitTracker.getHabits().size();
        if (targetHabits == 0) {
            targetHabits = 1; // Avoid division by zero
        }
        targetMeditationTime = mindfulnessData.getTargetMeditationTime();
        if (targetMeditationTime == 0) {
            targetMeditationTime = 10; // Default to 10 minutes if not set
        }
        
        // Update progress bar maximums
        stepsProgressBar.setMaximum(targetSteps);
        caloriesBurnedProgressBar.setMaximum(targetCaloriesBurned);
        waterProgressBar.setMaximum(targetWater);
        caloriesEatenProgressBar.setMaximum(targetCaloriesEaten);
        habitsCompletedProgressBar.setMaximum(targetHabits);
        mindfulnessProgressBar.setMaximum(targetMeditationTime);
        
        // Start counting animation
        animateToValue(fitnessData.getSteps(), fitnessData.getCaloriesBurned(), 
                      mealData.getWaterIntake(), mealData.getCaloriesEaten(),
                      habitTracker.getCompletedCount(), mindfulnessData.getMeditationTime());
    }
    
    private void animateToValue(int targetSteps, int targetCaloriesBurned, 
                               int targetWater, int targetCaloriesEaten, int targetHabitsCompleted, int targetMeditationTime) {
        // Stop existing animation if running
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        
        // Start new animation
        animationTimer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean stillAnimating = false;
                
                // Animate steps
                if (animatedSteps < targetSteps) {
                    int diff = targetSteps - animatedSteps;
                    animatedSteps += Math.max(1, diff / 20);
                    if (animatedSteps > targetSteps) animatedSteps = targetSteps;
                    stillAnimating = true;
                } else if (animatedSteps > targetSteps) {
                    animatedSteps = targetSteps;
                }
                
                // Animate calories burned
                if (animatedCaloriesBurned < targetCaloriesBurned) {
                    int diff = targetCaloriesBurned - animatedCaloriesBurned;
                    animatedCaloriesBurned += Math.max(1, diff / 20);
                    if (animatedCaloriesBurned > targetCaloriesBurned) animatedCaloriesBurned = targetCaloriesBurned;
                    stillAnimating = true;
                } else if (animatedCaloriesBurned > targetCaloriesBurned) {
                    animatedCaloriesBurned = targetCaloriesBurned;
                }
                
                // Animate water
                if (animatedWater < targetWater) {
                    int diff = targetWater - animatedWater;
                    animatedWater += Math.max(1, diff / 20);
                    if (animatedWater > targetWater) animatedWater = targetWater;
                    stillAnimating = true;
                } else if (animatedWater > targetWater) {
                    animatedWater = targetWater;
                }
                
                // Animate calories eaten
                if (animatedCaloriesEaten < targetCaloriesEaten) {
                    int diff = targetCaloriesEaten - animatedCaloriesEaten;
                    animatedCaloriesEaten += Math.max(1, diff / 20);
                    if (animatedCaloriesEaten > targetCaloriesEaten) animatedCaloriesEaten = targetCaloriesEaten;
                    stillAnimating = true;
                } else if (animatedCaloriesEaten > targetCaloriesEaten) {
                    animatedCaloriesEaten = targetCaloriesEaten;
                }
                
                // Animate habits completed
                if (animatedHabitsCompleted < targetHabitsCompleted) {
                    int diff = targetHabitsCompleted - animatedHabitsCompleted;
                    animatedHabitsCompleted += Math.max(1, diff / 20);
                    if (animatedHabitsCompleted > targetHabitsCompleted) animatedHabitsCompleted = targetHabitsCompleted;
                    stillAnimating = true;
                } else if (animatedHabitsCompleted > targetHabitsCompleted) {
                    animatedHabitsCompleted = targetHabitsCompleted;
                }
                
                // Animate meditation time
                if (animatedMeditationTime < targetMeditationTime) {
                    int diff = targetMeditationTime - animatedMeditationTime;
                    animatedMeditationTime += Math.max(1, diff / 20);
                    if (animatedMeditationTime > targetMeditationTime) animatedMeditationTime = targetMeditationTime;
                    stillAnimating = true;
                } else if (animatedMeditationTime > targetMeditationTime) {
                    animatedMeditationTime = targetMeditationTime;
                }
                
                // Update display
                updateDisplayInternal();
                
                // Stop if done
                if (!stillAnimating) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        animationTimer.start();
    }
    
    private void updateDisplayInternal() {
        // Update labels with proper formatting
        stepsLabel.setText(String.format("%d / %d", animatedSteps, targetSteps));
        caloriesBurnedLabel.setText(String.format("%d / %d", animatedCaloriesBurned, targetCaloriesBurned));
        waterIntakeLabel.setText(String.format("%d ml / %d ml", animatedWater, targetWater));
        caloriesEatenLabel.setText(String.format("%d / %d", animatedCaloriesEaten, targetCaloriesEaten));
        habitsCompletedLabel.setText(String.format("%d / %d", animatedHabitsCompleted, targetHabits));
        mindfulnessLabel.setText(String.format("%d min / %d min", animatedMeditationTime, targetMeditationTime));
        
        // Update progress bars with color coding
        updateProgressBar(stepsProgressBar, animatedSteps, targetSteps);
        updateProgressBar(caloriesBurnedProgressBar, animatedCaloriesBurned, targetCaloriesBurned);
        updateProgressBar(waterProgressBar, animatedWater, targetWater);
        updateProgressBar(caloriesEatenProgressBar, animatedCaloriesEaten, targetCaloriesEaten);
        updateProgressBar(habitsCompletedProgressBar, animatedHabitsCompleted, targetHabits);
        updateProgressBar(mindfulnessProgressBar, animatedMeditationTime, targetMeditationTime);
    }
    
    private void updateProgressBar(JProgressBar progressBar, int current, int target) {
        progressBar.setValue(current);
        
        // Color coding based on progress
        double percentage = target > 0 ? (double) current / target : 0.0;
        if (percentage >= 1.0) {
            // Green when goal is met or exceeded
            progressBar.setForeground(new Color(76, 175, 80));
        } else if (percentage >= 0.75) {
            // Light green when close
            progressBar.setForeground(new Color(139, 195, 74));
        } else if (percentage >= 0.50) {
            // Yellow when halfway
            progressBar.setForeground(new Color(255, 193, 7));
        } else if (percentage >= 0.25) {
            // Orange when starting
            progressBar.setForeground(new Color(255, 152, 0));
        } else {
            // Red when far from goal
            progressBar.setForeground(new Color(244, 67, 54));
        }
    }
}
