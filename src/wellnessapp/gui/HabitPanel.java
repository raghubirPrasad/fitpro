package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.models.Habit;
import wellnessapp.models.HabitTracker;
import wellnessapp.utils.FileHandler;
import wellnessapp.utils.AnimatedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * HabitPanel for tracking habits
 * Demonstrates: GUI components, event handling, Iterator usage
 */
public class HabitPanel extends JPanel {
    private User user;
    private FileHandler fileHandler;
    private HabitTracker habitTracker;
    
    private JTextField habitNameField;
    private JList<String> habitsList;
    private DefaultListModel<String> listModel;
    private JLabel completedLabel;
    private float alpha = 0.0f; // For fade-in effect
    
    public HabitPanel(User user) {
        this.user = user;
        this.fileHandler = FileHandler.getInstance();
        loadData();
        
        setLayout(new BorderLayout());
        setOpaque(true);
        
        // Start fade-in animation
        startFadeInAnimation();
        
        // Title with subtle shadow
        JLabel titleLabel = new JLabel("Habit Tracking") {
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
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Habit Name:"), gbc);
        gbc.gridx = 1;
        habitNameField = new JTextField(15);
        inputPanel.add(habitNameField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        AnimatedButton addButton = new AnimatedButton("Add Habit");
        AnimatedButton toggleButton = new AnimatedButton("Toggle Selected");
        toggleButton.setButtonColors(new Color(76, 175, 80), new Color(56, 142, 60), new Color(46, 125, 50));
        AnimatedButton removeButton = new AnimatedButton("Remove Selected");
        removeButton.setButtonColors(new Color(244, 67, 54), new Color(211, 47, 47), new Color(198, 40, 40));
        AnimatedButton newDayButton = new AnimatedButton("New Day");
        newDayButton.setButtonColors(new Color(255, 152, 0), new Color(255, 193, 7), new Color(255, 143, 0));
        buttonPanel.add(addButton);
        buttonPanel.add(toggleButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(newDayButton);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);
        
        completedLabel = new JLabel("Completed: 0 / " + habitTracker.getHabits().size(), JLabel.CENTER);
        completedLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 2;
        inputPanel.add(completedLabel, gbc);
        
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        
        // Habits list
        listModel = new DefaultListModel<String>();
        updateListModel();
        habitsList = new JList<String>(listModel);
        habitsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(habitsList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Event handlers
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddHabit();
            }
        });
        
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleToggleHabit();
            }
        });
        
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRemoveHabit();
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
    
    private void handleAddHabit() {
        String habitName = habitNameField.getText().trim();
        if (habitName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter habit name", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        habitTracker.addHabit(habitName);
        fileHandler.saveHabitTracker(user.getUsername(), habitTracker);
        
        updateDisplay();
        habitNameField.setText("");
        
        // Visual feedback - highlight new habit
        animateHabitAdded();
        
        JOptionPane.showMessageDialog(this, "Habit added successfully!", 
            "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void handleToggleHabit() {
        int selectedIndex = habitsList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a habit", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Habit selectedHabit = habitTracker.getHabits().get(selectedIndex);
        habitTracker.toggleHabit(selectedHabit.getName());
        fileHandler.saveHabitTracker(user.getUsername(), habitTracker);
        
        updateDisplay();
    }
    
    private void handleRemoveHabit() {
        int selectedIndex = habitsList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a habit", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Habit selectedHabit = habitTracker.getHabits().get(selectedIndex);
        habitTracker.removeHabit(selectedHabit.getName());
        fileHandler.saveHabitTracker(user.getUsername(), habitTracker);
        
        updateDisplay();
    }
    
    private void handleNewDay() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Reset all habits for a new day?", "Confirm", 
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            habitTracker.reset();
            fileHandler.saveHabitTracker(user.getUsername(), habitTracker);
            updateDisplay();
            JOptionPane.showMessageDialog(this, "Habits reset for new day!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void animateHabitAdded() {
        // Flash effect on completed label
        Timer flashTimer = new Timer(50, new ActionListener() {
            private int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 3) {
                    Color originalColor = completedLabel.getForeground();
                    completedLabel.setForeground(count % 2 == 0 ? new Color(76, 175, 80) : originalColor);
                    count++;
                } else {
                    completedLabel.setForeground(Color.BLACK);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        flashTimer.start();
    }
    
    private void updateDisplay() {
        updateListModel();
        animateNumberChange(completedLabel, "Completed: ", habitTracker.getCompletedCount(), 
            " / " + habitTracker.getHabits().size());
    }
    
    private void animateNumberChange(JLabel label, String prefix, int targetValue, String suffix) {
        String currentText = label.getText();
        int initialValue = 0;
        try {
            String valueStr = currentText.replace(prefix, "").split(" / ")[0].trim();
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
    
    private void updateListModel() {
        listModel.clear();
        for (Habit habit : habitTracker.getHabits()) {
            listModel.addElement(habit.toString());
        }
    }
    
    private void loadData() {
        habitTracker = fileHandler.loadHabitTrackers().get(user.getUsername());
        if (habitTracker == null) {
            habitTracker = new HabitTracker();
        }
    }
}

