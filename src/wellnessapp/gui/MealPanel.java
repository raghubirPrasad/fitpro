package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.models.MealData;
import wellnessapp.models.MealItem;
import wellnessapp.models.MealsData;
import wellnessapp.utils.FileHandler;
import wellnessapp.utils.Validator;
import wellnessapp.utils.AnimatedButton;
import wellnessapp.exceptions.InvalidInputException;
import wellnessapp.exceptions.NegativeValueException;
import wellnessapp.exceptions.DecimalValueException;
import wellnessapp.exceptions.SpecialCharacterException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * MealPanel for tracking meals and water intake
 * Demonstrates: GUI components, event handling, HashMap usage, abstract class inheritance
 */
public class MealPanel extends BasePanel {
    private MealData mealData;
    
    // Water section
    private JTextField waterField;
    private JLabel currentWaterLabel;
    private JLabel currentCaloriesLabel;
    
    // Meals section
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> itemComboBox;
    private JTextField miscItemNameField;
    private JTextField miscCaloriesField;
    private JLabel miscItemNameLabel;
    private JLabel miscCaloriesLabel;
    private JLabel servingSizeLabel;
    private JLabel caloriesLabel;
    private AnimatedButton addToTotalButton;
    
    // Targets section
    private JTextField targetWaterField;
    private JTextField targetCaloriesField;
    
    // Meals list
    private JList<String> mealsList;
    private DefaultListModel<String> mealsListModel;
    
    public MealPanel(User user) {
        super(user); // Initialize BasePanel (sets user, fileHandler, layout, fade-in)
        loadData();
        
        // Title with subtle shadow
        JLabel titleLabel = new JLabel("Meal Tracking") {
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
        currentWaterLabel = new JLabel("Water: " + mealData.getWaterIntake() + " ml");
        inputPanel.add(currentWaterLabel, gbc);
        
        gbc.gridy = 2;
        currentCaloriesLabel = new JLabel("Calories: " + mealData.getCaloriesEaten() + " kcal");
        inputPanel.add(currentCaloriesLabel, gbc);
        
        // Separator
        gbc.gridy = 3;
        inputPanel.add(new JSeparator(), gbc);
        
        // Add Water section
        gbc.gridwidth = 1;
        gbc.gridy = 4;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Add Water (ml):"), gbc);
        gbc.gridx = 1;
        waterField = new JTextField(10);
        inputPanel.add(waterField, gbc);
        
        // Add Water button
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        AnimatedButton addWaterButton = new AnimatedButton("Add Water");
        addWaterButton.setButtonColors(new Color(33, 150, 243), new Color(25, 118, 210), new Color(21, 101, 192));
        inputPanel.add(addWaterButton, gbc);
        
        // Separator before meals section
        gbc.gridy = 6;
        inputPanel.add(new JSeparator(), gbc);
        
        // Meals section header
        gbc.gridy = 7;
        inputPanel.add(new JLabel("Meal Selection:"), gbc);
        
        // Category dropdown
        gbc.gridwidth = 1;
        gbc.gridy = 8;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryComboBox = new JComboBox<>(new String[]{"Select Category", "Breakfast", "Lunch", "Dinner", "Snacks", "Misc"});
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCategoryChange();
            }
        });
        inputPanel.add(categoryComboBox, gbc);
        
        // Item dropdown
        gbc.gridy = 9;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Item:"), gbc);
        gbc.gridx = 1;
        itemComboBox = new JComboBox<>(new String[]{"Select Item"});
        itemComboBox.setEnabled(false);
        itemComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleItemChange();
            }
        });
        inputPanel.add(itemComboBox, gbc);
        
        // Misc item name field
        gbc.gridy = 10;
        gbc.gridx = 0;
        miscItemNameLabel = new JLabel("Item Name:");
        miscItemNameLabel.setVisible(false);
        inputPanel.add(miscItemNameLabel, gbc);
        gbc.gridx = 1;
        miscItemNameField = new JTextField(10);
        miscItemNameField.setVisible(false);
        miscItemNameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkMiscFields();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                checkMiscFields();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                checkMiscFields();
            }
        });
        inputPanel.add(miscItemNameField, gbc);
        
        // Misc calories field
        gbc.gridy = 11;
        gbc.gridx = 0;
        miscCaloriesLabel = new JLabel("Calories:");
        miscCaloriesLabel.setVisible(false);
        inputPanel.add(miscCaloriesLabel, gbc);
        gbc.gridx = 1;
        miscCaloriesField = new JTextField(10);
        miscCaloriesField.setVisible(false);
        miscCaloriesField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkMiscFields();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                checkMiscFields();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                checkMiscFields();
            }
        });
        inputPanel.add(miscCaloriesField, gbc);
        
        // Serving size label
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        servingSizeLabel = new JLabel("Serving Size: -");
        servingSizeLabel.setVisible(false);
        inputPanel.add(servingSizeLabel, gbc);
        
        // Calories label
        gbc.gridy = 13;
        caloriesLabel = new JLabel("Calories: - kcal");
        caloriesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        caloriesLabel.setForeground(new Color(76, 175, 80));
        caloriesLabel.setVisible(false);
        inputPanel.add(caloriesLabel, gbc);
        
        // Add to Total button
        gbc.gridy = 14;
        addToTotalButton = new AnimatedButton("Add to Total");
        addToTotalButton.setEnabled(false);
        inputPanel.add(addToTotalButton, gbc);
        
        // Separator before targets
        gbc.gridy = 15;
        inputPanel.add(new JSeparator(), gbc);
        
        // Targets section
        gbc.gridwidth = 1;
        gbc.gridy = 16;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Target Water (ml):"), gbc);
        gbc.gridx = 1;
        targetWaterField = new JTextField(10);
        targetWaterField.setText(String.valueOf(mealData.getTargetWater()));
        inputPanel.add(targetWaterField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 17;
        inputPanel.add(new JLabel("Target Calories:"), gbc);
        gbc.gridx = 1;
        targetCaloriesField = new JTextField(10);
        targetCaloriesField.setText(String.valueOf(mealData.getTargetCalories()));
        inputPanel.add(targetCaloriesField, gbc);
        
        // Update Targets button
        gbc.gridx = 0;
        gbc.gridy = 18;
        gbc.gridwidth = 2;
        AnimatedButton updateTargetsButton = new AnimatedButton("Update Targets");
        inputPanel.add(updateTargetsButton, gbc);
        
        // Right panel - Meals list
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JLabel("Meals Today:"), BorderLayout.NORTH);
        
        mealsListModel = new DefaultListModel<>();
        mealsList = new JList<>(mealsListModel);
        mealsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(mealsList);
        listPanel.add(listScrollPane, BorderLayout.CENTER);
        
        // Remove Selected button
        JPanel buttonListPanel = new JPanel(new FlowLayout());
        AnimatedButton removeSelectedButton = new AnimatedButton("Remove Selected");
        removeSelectedButton.setButtonColors(new Color(244, 67, 54), new Color(211, 47, 47), new Color(198, 40, 40));
        removeSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRemoveSelected();
            }
        });
        buttonListPanel.add(removeSelectedButton);
        listPanel.add(buttonListPanel, BorderLayout.SOUTH);
        
        mainPanel.add(inputPanel);
        mainPanel.add(listPanel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        updateMealsList();
        
        // Event handlers
        addWaterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddWater();
            }
        });
        
        addToTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddToTotal();
            }
        });
        
        updateTargetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateTargets();
            }
        });
    }
    
    private void handleCategoryChange() {
        String category = (String) categoryComboBox.getSelectedItem();
        itemComboBox.removeAllItems();
        
        if ("Misc".equals(category)) {
            itemComboBox.addItem("Manual Entry");
            itemComboBox.setEnabled(false);
            showMiscFields();
        } else if (category != null && !category.equals("Select Category")) {
            HashMap<String, MealItem> items = MealsData.getMealItems(category);
            if (items != null) {
                itemComboBox.addItem("Select Item");
                for (String itemName : items.keySet()) {
                    itemComboBox.addItem(itemName);
                }
                itemComboBox.setEnabled(true);
            }
            hideMiscFields();
        } else {
            itemComboBox.addItem("Select Item");
            itemComboBox.setEnabled(false);
            hideMiscFields();
        }
        
        hideMealInfo();
    }
    
    private void handleItemChange() {
        String category = (String) categoryComboBox.getSelectedItem();
        String itemName = (String) itemComboBox.getSelectedItem();
        
        if (itemName == null || itemName.equals("Select Item") || itemName.equals("Manual Entry")) {
            hideMealInfo();
            return;
        }
        
        if ("Misc".equals(category)) {
            showMiscFields();
            // Enable button when misc fields have values
            checkMiscFields();
        } else {
            hideMiscFields();
            HashMap<String, MealItem> items = MealsData.getMealItems(category);
            if (items != null) {
                MealItem item = items.get(itemName);
                if (item != null) {
                    servingSizeLabel.setText("Serving Size: " + item.getServingSize());
                    caloriesLabel.setText("Calories: " + item.getCalories() + " kcal");
                    servingSizeLabel.setVisible(true);
                    caloriesLabel.setVisible(true);
                    addToTotalButton.setEnabled(true);
                }
            }
        }
    }
    
    private void checkMiscFields() {
        String itemName = miscItemNameField.getText().trim();
        String caloriesStr = miscCaloriesField.getText().trim();
        addToTotalButton.setEnabled(!itemName.isEmpty() && !caloriesStr.isEmpty());
    }
    
    private void showMiscFields() {
        miscItemNameLabel.setVisible(true);
        miscItemNameField.setVisible(true);
        miscCaloriesLabel.setVisible(true);
        miscCaloriesField.setVisible(true);
        servingSizeLabel.setVisible(false);
        caloriesLabel.setVisible(false);
    }
    
    private void hideMiscFields() {
        miscItemNameLabel.setVisible(false);
        miscItemNameField.setVisible(false);
        miscCaloriesLabel.setVisible(false);
        miscCaloriesField.setVisible(false);
    }
    
    private void hideMealInfo() {
        servingSizeLabel.setVisible(false);
        caloriesLabel.setVisible(false);
        addToTotalButton.setEnabled(false);
    }
    
    private void handleAddWater() {
        try {
            String waterStr = waterField.getText().trim();
            if (waterStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter water amount", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int water = Validator.parsePositiveInteger(waterStr, "Water");
            mealData.setWaterIntake(mealData.getWaterIntake() + water);
            fileHandler.saveMealData(user.getUsername(), mealData);
            updateDisplay();
            
            waterField.setText("");
            animateSuccessFeedback();
            
            JOptionPane.showMessageDialog(this, "Water added successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidInputException | NegativeValueException | DecimalValueException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleAddToTotal() {
        try {
            String category = (String) categoryComboBox.getSelectedItem();
            String itemName = (String) itemComboBox.getSelectedItem();
            int calories = 0;
            String mealEntry = "";
            
            if (category == null || category.equals("Select Category")) {
                JOptionPane.showMessageDialog(this, "Please select a category.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if ("Misc".equals(category)) {
                String miscName = miscItemNameField.getText().trim();
                String caloriesStr = miscCaloriesField.getText().trim();
                
                if (miscName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter item name.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate item name (alphabets and numbers only)
                miscName = Validator.validateAlphabetsAndNumbers(miscName, "Item Name");
                
                if (caloriesStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter calories.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                calories = Validator.parsePositiveInteger(caloriesStr, "Calories");
                mealEntry = miscName; // Just the name, calories will be added by addEntry()
            } else {
                if (itemName == null || itemName.equals("Select Item")) {
                    JOptionPane.showMessageDialog(this, "Please select an item.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                HashMap<String, MealItem> items = MealsData.getMealItems(category);
                if (items != null) {
                    MealItem item = items.get(itemName);
                    if (item != null) {
                        calories = item.getCalories();
                        // Format: "Item Name (Serving Size)" - calories will be added by addEntry()
                        mealEntry = item.getName() + " (" + item.getServingSize() + ")";
                    }
                }
            }
            
            if (calories > 0) {
                mealData.addMeal(mealEntry, calories);
                fileHandler.saveMealData(user.getUsername(), mealData);
                updateDisplay();
                
                // Clear fields
                if ("Misc".equals(category)) {
                    miscItemNameField.setText("");
                    miscCaloriesField.setText("");
                }
                hideMealInfo();
                
                // Scroll to bottom
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (mealsListModel.getSize() > 0) {
                            mealsList.ensureIndexIsVisible(mealsListModel.getSize() - 1);
                        }
                    }
                });
                
                animateSuccessFeedback();
                
                JOptionPane.showMessageDialog(this, "Meal added successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (InvalidInputException | NegativeValueException | DecimalValueException | SpecialCharacterException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleUpdateTargets() {
        try {
            String targetWaterStr = targetWaterField.getText().trim();
            String targetCaloriesStr = targetCaloriesField.getText().trim();
            
            if (targetWaterStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter target water.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (targetCaloriesStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter target calories.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int targetWater = Validator.parsePositiveInteger(targetWaterStr, "Target Water");
            int targetCalories = Validator.parsePositiveInteger(targetCaloriesStr, "Target Calories");
            
            mealData.setTargetWater(targetWater);
            mealData.setTargetCalories(targetCalories);
            
            fileHandler.saveMealData(user.getUsername(), mealData);
            JOptionPane.showMessageDialog(this, "Targets updated successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidInputException | NegativeValueException | DecimalValueException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleRemoveSelected() {
        int selectedIndex = mealsList.getSelectedIndex();
        if (selectedIndex >= 0) {
            mealData.removeMeal(selectedIndex);
            fileHandler.saveMealData(user.getUsername(), mealData);
            updateDisplay();
            updateMealsList();
        }
    }
    
    private void animateSuccessFeedback() {
        Timer flashTimer = new Timer(50, new ActionListener() {
            private int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 3) {
                    Color originalColor = currentCaloriesLabel.getForeground();
                    currentCaloriesLabel.setForeground(count % 2 == 0 ? new Color(76, 175, 80) : originalColor);
                    count++;
                } else {
                    currentCaloriesLabel.setForeground(Color.BLACK);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        flashTimer.start();
    }
    
    @Override
    protected void updateDisplay() {
        animateNumberChange(currentWaterLabel, "Water: ", mealData.getWaterIntake(), " ml");
        animateNumberChange(currentCaloriesLabel, "Calories: ", mealData.getCaloriesEaten(), " kcal");
        updateMealsList();
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
    
    private void updateMealsList() {
        mealsListModel.clear();
        for (String meal : mealData.getMeals()) {
            mealsListModel.addElement(meal);
        }
    }
    
    @Override
    protected void loadData() {
        mealData = fileHandler.loadMealData().get(user.getUsername());
        if (mealData == null) {
            mealData = new MealData();
        }
    }
    
    // Method to refresh data from storage (called when switching to this panel)
    public void refreshData() {
        MealData updatedData = fileHandler.loadMealData().get(user.getUsername());
        if (updatedData != null) {
            mealData = updatedData;
            if (targetCaloriesField != null) {
                targetCaloriesField.setText(String.valueOf(mealData.getTargetCalories()));
            }
            if (targetWaterField != null) {
                targetWaterField.setText(String.valueOf(mealData.getTargetWater()));
            }
            updateDisplay();
        }
    }
}
