package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.utils.FileHandler;
import wellnessapp.utils.Validator;
import wellnessapp.exceptions.InvalidInputException;
import wellnessapp.exceptions.NegativeValueException;
import wellnessapp.exceptions.DecimalValueException;
import wellnessapp.exceptions.NumericValueException;
import wellnessapp.exceptions.SpecialCharacterException;
import wellnessapp.exceptions.ZeroValueException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * LoginFrame for user authentication
 * Demonstrates: GUI components, event handling, action listeners
 */
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createUserButton;
    private FileHandler fileHandler;
    private Map<String, User> users;
    
    public LoginFrame() {
        fileHandler = FileHandler.getInstance();
        users = fileHandler.loadUsers();
        
        setTitle("Wellness App - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        mainPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        mainPanel.add(passwordField, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        loginButton = new JButton("Login");
        createUserButton = new JButton("Create User");
        buttonPanel.add(loginButton);
        buttonPanel.add(createUserButton);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Event handlers
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCreateUser();
            }
        });
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(this, "Login successful!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new MainFrame(user).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleCreateUser() {
        CreateUserDialog dialog = new CreateUserDialog(this);
        dialog.setVisible(true);
        
        if (dialog.isUserCreated()) {
            User newUser = dialog.getCreatedUser();
            users.put(newUser.getUsername(), newUser);
            // User stored in memory only - no persistence
            JOptionPane.showMessageDialog(this, "User created successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Inner class for create user dialog
    private class CreateUserDialog extends JDialog {
        private JTextField usernameField;
        private JPasswordField passwordField;
        private JTextField nameField;
        private JTextField ageField;
        private JComboBox<String> genderComboBox;
        private JTextField heightField;
        private JTextField weightField;
        private boolean userCreated;
        private User createdUser;
        
        public CreateUserDialog(JFrame parent) {
            super(parent, "Create New User", true);
            setSize(350, 400);
            setLocationRelativeTo(parent);
            setLayout(new GridBagLayout());
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            
            // Username
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(new JLabel("Username:"), gbc);
            gbc.gridx = 1;
            usernameField = new JTextField(15);
            add(usernameField, gbc);
            
            // Password
            gbc.gridx = 0;
            gbc.gridy = 1;
            add(new JLabel("Password:"), gbc);
            gbc.gridx = 1;
            passwordField = new JPasswordField(15);
            add(passwordField, gbc);
            
            // Name
            gbc.gridx = 0;
            gbc.gridy = 2;
            add(new JLabel("Name:"), gbc);
            gbc.gridx = 1;
            nameField = new JTextField(15);
            add(nameField, gbc);
            
            // Age
            gbc.gridx = 0;
            gbc.gridy = 3;
            add(new JLabel("Age:"), gbc);
            gbc.gridx = 1;
            ageField = new JTextField(15);
            add(ageField, gbc);
            
            // Gender
            gbc.gridx = 0;
            gbc.gridy = 4;
            add(new JLabel("Gender:"), gbc);
            gbc.gridx = 1;
            String[] genders = {"Male", "Female"};
            genderComboBox = new JComboBox<>(genders);
            add(genderComboBox, gbc);
            
            // Height
            gbc.gridx = 0;
            gbc.gridy = 5;
            add(new JLabel("Height (cm):"), gbc);
            gbc.gridx = 1;
            heightField = new JTextField(15);
            add(heightField, gbc);
            
            // Weight
            gbc.gridx = 0;
            gbc.gridy = 6;
            add(new JLabel("Weight (kg):"), gbc);
            gbc.gridx = 1;
            weightField = new JTextField(15);
            add(weightField, gbc);
            
            // Buttons
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton createButton = new JButton("Create");
            JButton cancelButton = new JButton("Cancel");
            buttonPanel.add(createButton);
            buttonPanel.add(cancelButton);
            
            gbc.gridx = 0;
            gbc.gridy = 7;
            gbc.gridwidth = 2;
            add(buttonPanel, gbc);
            
            userCreated = false;
            
            createButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String username = usernameField.getText().trim();
                        String password = new String(passwordField.getPassword());
                        String name = nameField.getText().trim();
                        String ageStr = ageField.getText().trim();
                        String gender = (String) genderComboBox.getSelectedItem();
                        String heightStr = heightField.getText().trim();
                        String weightStr = weightField.getText().trim();
                        
                        if (password.isEmpty() || ageStr.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty()) {
                            JOptionPane.showMessageDialog(CreateUserDialog.this, 
                                "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        
                        // Validate username (alphabets, numbers, underscore only)
                        username = Validator.validateUsername(username, "Username");
                        
                        if (users.containsKey(username)) {
                            JOptionPane.showMessageDialog(CreateUserDialog.this, 
                                "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        
                        // Validate name (alphabets only)
                        name = Validator.validateName(name, "Name");
                        
                        // Validate age (no decimals, no negatives, no zero)
                        int age = Validator.parseAge(ageStr, "Age");
                        double height = Validator.parsePositiveDoubleNoZero(heightStr, "Height");
                        double weight = Validator.parsePositiveDoubleNoZero(weightStr, "Weight");
                        
                        createdUser = new User(username, password, name, age, gender, height, weight);
                        userCreated = true;
                        dispose();
                    } catch (InvalidInputException | NegativeValueException | DecimalValueException | 
                             NumericValueException | SpecialCharacterException | ZeroValueException ex) {
                        JOptionPane.showMessageDialog(CreateUserDialog.this, 
                            ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        
        public boolean isUserCreated() {
            return userCreated;
        }
        
        public User getCreatedUser() {
            return createdUser;
        }
    }
}

