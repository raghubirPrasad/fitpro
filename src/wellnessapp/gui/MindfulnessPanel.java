package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.models.MindfulnessData;
import wellnessapp.utils.FileHandler;
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
 * MindfulnessPanel for tracking meditation time
 * Demonstrates: GUI components, event handling, exception handling, abstract class inheritance
 */
public class MindfulnessPanel extends BasePanel {
    private MindfulnessData mindfulnessData;
    
    private JTextField meditationTimeField;
    private JTextField targetMeditationTimeField;
    private JLabel currentMeditationLabel;
    private JLabel currentMoodLabel;
    private JComboBox<String> moodComboBox;
    public MindfulnessPanel(User user) {
        super(user);
        loadData();
        
        JLabel titleLabel = new JLabel("Mindfulness & Meditation") {
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
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(new JLabel("Current Stats:"), gbc);
        
        gbc.gridy = 1;
        currentMeditationLabel = new JLabel("Meditation Time: " + mindfulnessData.getMeditationTime() + " minutes");
        currentMeditationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(currentMeditationLabel, gbc);
        
        gbc.gridy = 2;
        String moodDisplay = mindfulnessData.getMood().isEmpty() ? "Not set" : mindfulnessData.getMood();
        currentMoodLabel = new JLabel("Mood: " + moodDisplay);
        currentMoodLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(currentMoodLabel, gbc);
        
        gbc.gridy = 3;
        mainPanel.add(new JSeparator(), gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 4;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Mood:"), gbc);
        gbc.gridx = 1;
        moodComboBox = new JComboBox<>(new String[]{"Select Mood", "Happy", "Neutral", "Sad", "Anxious", "Angry"});
        if (!mindfulnessData.getMood().isEmpty()) {
            moodComboBox.setSelectedItem(mindfulnessData.getMood());
        }
        moodComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMoodChange();
            }
        });
        mainPanel.add(moodComboBox, gbc);
        
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        mainPanel.add(new JSeparator(), gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 6;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Add Meditation Time (minutes):"), gbc);
        gbc.gridx = 1;
        meditationTimeField = new JTextField(15);
        mainPanel.add(meditationTimeField, gbc);
        
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        AnimatedButton addMeditationButton = new AnimatedButton("Add Meditation Time");
        addMeditationButton.setButtonColors(new Color(156, 39, 176), new Color(142, 36, 170), new Color(123, 31, 162));
        mainPanel.add(addMeditationButton, gbc);
        
        gbc.gridy = 8;
        mainPanel.add(new JSeparator(), gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 9;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Target Meditation Time (minutes):"), gbc);
        gbc.gridx = 1;
        targetMeditationTimeField = new JTextField(15);
        targetMeditationTimeField.setText(String.valueOf(mindfulnessData.getTargetMeditationTime()));
        mainPanel.add(targetMeditationTimeField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        AnimatedButton updateTargetButton = new AnimatedButton("Update Target");
        mainPanel.add(updateTargetButton, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        addMeditationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddMeditationTime();
            }
        });
        
        updateTargetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateTarget();
            }
        });
    }
    
    private void handleMoodChange() {
        String selectedMood = (String) moodComboBox.getSelectedItem();
        if (selectedMood != null && !selectedMood.equals("Select Mood")) {
            mindfulnessData.setMood(selectedMood);
            fileHandler.saveMindfulnessData(user.getUsername(), mindfulnessData);
            updateDisplay();
        }
    }
    
    private void handleAddMeditationTime() {
        try {
            String timeStr = meditationTimeField.getText().trim();
            if (timeStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter meditation time", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int minutes = Validator.parsePositiveInteger(timeStr, "Meditation Time");
            mindfulnessData.addMeditationTime(minutes);
            fileHandler.saveMindfulnessData(user.getUsername(), mindfulnessData);
            updateDisplay();
            
            meditationTimeField.setText("");
            animateSuccessFeedback();
            
            JOptionPane.showMessageDialog(this, "Meditation time added successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidInputException | NegativeValueException | DecimalValueException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleUpdateTarget() {
        try {
            String targetStr = targetMeditationTimeField.getText().trim();
            if (targetStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter target meditation time", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int target = Validator.parsePositiveInteger(targetStr, "Target Meditation Time");
            mindfulnessData.setTargetMeditationTime(target);
            fileHandler.saveMindfulnessData(user.getUsername(), mindfulnessData);
            
            JOptionPane.showMessageDialog(this, "Target updated successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidInputException | NegativeValueException | DecimalValueException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void animateSuccessFeedback() {
        Timer flashTimer = new Timer(50, new ActionListener() {
            private int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 3) {
                    Color originalColor = currentMeditationLabel.getForeground();
                    currentMeditationLabel.setForeground(count % 2 == 0 ? new Color(156, 39, 176) : originalColor);
                    count++;
                } else {
                    currentMeditationLabel.setForeground(Color.BLACK);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        flashTimer.start();
    }
    
    @Override
    protected void updateDisplay() {
        animateNumberChange(currentMeditationLabel, "Meditation Time: ", mindfulnessData.getMeditationTime(), " minutes");
        String moodDisplay = mindfulnessData.getMood().isEmpty() ? "Not set" : mindfulnessData.getMood();
        currentMoodLabel.setText("Mood: " + moodDisplay);
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
    
    @Override
    protected void loadData() {
        mindfulnessData = fileHandler.loadMindfulnessData().get(user.getUsername());
        if (mindfulnessData == null) {
            mindfulnessData = new MindfulnessData();
        }
    }
    
    public void refreshData() {
        MindfulnessData updatedData = fileHandler.loadMindfulnessData().get(user.getUsername());
        if (updatedData != null) {
            mindfulnessData = updatedData;
            if (targetMeditationTimeField != null) {
                targetMeditationTimeField.setText(String.valueOf(mindfulnessData.getTargetMeditationTime()));
            }
            updateDisplay();
        }
    }
}

