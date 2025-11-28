package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.models.Habit;
import wellnessapp.models.HabitTracker;
import wellnessapp.utils.FileHandler;

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
    
    public HabitPanel(User user) {
        this.user = user;
        this.fileHandler = FileHandler.getInstance();
        loadData();
        
        setLayout(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("Habit Tracking", JLabel.CENTER);
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
        JButton addButton = new JButton("Add Habit");
        JButton toggleButton = new JButton("Toggle Selected");
        JButton removeButton = new JButton("Remove Selected");
        JButton newDayButton = new JButton("New Day");
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
    
    private void updateDisplay() {
        updateListModel();
        completedLabel.setText("Completed: " + habitTracker.getCompletedCount() + 
            " / " + habitTracker.getHabits().size());
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

