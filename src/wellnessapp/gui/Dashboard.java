package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.models.FitnessData;
import wellnessapp.models.MealData;
import wellnessapp.utils.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dashboard panel showing key metrics with animations
 * Demonstrates: GUI components, layout managers, animations, Timer class
 */
public class Dashboard extends JPanel {
    private JLabel stepsLabel;
    private JLabel caloriesBurnedLabel;
    private JLabel waterIntakeLabel;
    private JLabel caloriesEatenLabel;
    private JProgressBar stepsProgressBar;
    private JProgressBar caloriesBurnedProgressBar;
    private JProgressBar waterProgressBar;
    private JProgressBar caloriesEatenProgressBar;
    private User user;
    private FileHandler fileHandler;
    
    // Animation values
    private int animatedSteps = 0;
    private int animatedCaloriesBurned = 0;
    private int animatedWater = 0;
    private int animatedCaloriesEaten = 0;
    
    // Target values for animation
    private int targetSteps = 10000;
    private int targetCaloriesBurned = 500;
    private int targetWater = 2000;
    private int targetCaloriesEaten = 2000;
    
    private Timer animationTimer;
    private float alpha = 0.0f; // For fade-in effect
    
    public Dashboard(User user) {
        this.user = user;
        this.fileHandler = FileHandler.getInstance();
        setLayout(new BorderLayout());
        setOpaque(true);
        
        // Start fade-in animation
        startFadeInAnimation();
        
        // Title with subtle shadow effect
        JLabel titleLabel = new JLabel("Dashboard") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // Draw text shadow
                g2d.setColor(new Color(0, 0, 0, 30));
                FontMetrics fm = g2d.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), textX + 2, textY + 2);
                
                // Draw main text
                g2d.setColor(getForeground());
                g2d.drawString(getText(), textX, textY);
                
                g2d.dispose();
            }
        };
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        // Main panel using GridBagLayout to match FitnessPanel alignment
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Steps
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
        
        // Calories Burned
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
        
        // Water Intake
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
        
        // Calories Eaten
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
        
        add(mainPanel, BorderLayout.CENTER);
        
        updateMetrics();
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
    
    public void updateMetrics() {
        FitnessData fitnessData = fileHandler.loadFitnessData().get(user.getUsername());
        MealData mealData = fileHandler.loadMealData().get(user.getUsername());
        
        if (fitnessData == null) {
            fitnessData = new wellnessapp.models.FitnessData();
        }
        if (mealData == null) {
            mealData = new wellnessapp.models.MealData();
        }
        
        // Get target values
        targetSteps = fitnessData.getTargetSteps();
        targetCaloriesBurned = fitnessData.getTargetCalories();
        targetWater = mealData.getTargetWater();
        targetCaloriesEaten = mealData.getTargetCalories();
        
        // Update progress bar maximums
        stepsProgressBar.setMaximum(targetSteps);
        caloriesBurnedProgressBar.setMaximum(targetCaloriesBurned);
        waterProgressBar.setMaximum(targetWater);
        caloriesEatenProgressBar.setMaximum(targetCaloriesEaten);
        
        // Start counting animation
        animateToValue(fitnessData.getSteps(), fitnessData.getCaloriesBurned(), 
                      mealData.getWaterIntake(), mealData.getCaloriesEaten());
    }
    
    private void animateToValue(int targetSteps, int targetCaloriesBurned, 
                               int targetWater, int targetCaloriesEaten) {
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
                
                // Update display
                updateDisplay();
                
                // Stop if done
                if (!stillAnimating) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        animationTimer.start();
    }
    
    private void updateDisplay() {
        // Update labels with proper formatting
        stepsLabel.setText(String.format("%d / %d", animatedSteps, targetSteps));
        caloriesBurnedLabel.setText(String.format("%d / %d", animatedCaloriesBurned, targetCaloriesBurned));
        waterIntakeLabel.setText(String.format("%d ml / %d ml", animatedWater, targetWater));
        caloriesEatenLabel.setText(String.format("%d / %d", animatedCaloriesEaten, targetCaloriesEaten));
        
        // Update progress bars with color coding
        updateProgressBar(stepsProgressBar, animatedSteps, targetSteps);
        updateProgressBar(caloriesBurnedProgressBar, animatedCaloriesBurned, targetCaloriesBurned);
        updateProgressBar(waterProgressBar, animatedWater, targetWater);
        updateProgressBar(caloriesEatenProgressBar, animatedCaloriesEaten, targetCaloriesEaten);
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

