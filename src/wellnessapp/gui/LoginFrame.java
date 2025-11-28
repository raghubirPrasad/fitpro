package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.utils.FileHandler;
import wellnessapp.utils.Validator;
import wellnessapp.exceptions.InvalidInputException;

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
            fileHandler.saveUsers(users);
            JOptionPane.showMessageDialog(this, "User created successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Inner class for create user dialog
    private class CreateUserDialog extends JDialog {
        private JTextField usernameField;
        private JPasswordField passwordField;
        private JTextField heightField;
        private JTextField weightField;
        private boolean userCreated;
        private User createdUser;
        
        public CreateUserDialog(JFrame parent) {
            super(parent, "Create New User", true);
            setSize(350, 250);
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
            
            // Height
            gbc.gridx = 0;
            gbc.gridy = 2;
            add(new JLabel("Height (cm):"), gbc);
            gbc.gridx = 1;
            heightField = new JTextField(15);
            add(heightField, gbc);
            
            // Weight
            gbc.gridx = 0;
            gbc.gridy = 3;
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
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            add(buttonPanel, gbc);
            
            userCreated = false;
            
            createButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String username = usernameField.getText().trim();
                        String password = new String(passwordField.getPassword());
                        String heightStr = heightField.getText().trim();
                        String weightStr = weightField.getText().trim();
                        
                        if (username.isEmpty() || password.isEmpty() || 
                            heightStr.isEmpty() || weightStr.isEmpty()) {
                            JOptionPane.showMessageDialog(CreateUserDialog.this, 
                                "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        
                        if (users.containsKey(username)) {
                            JOptionPane.showMessageDialog(CreateUserDialog.this, 
                                "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        
                        double height = Validator.parsePositiveDouble(heightStr, "Height");
                        double weight = Validator.parsePositiveDouble(weightStr, "Weight");
                        
                        createdUser = new User(username, password, height, weight);
                        userCreated = true;
                        dispose();
                    } catch (InvalidInputException ex) {
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

