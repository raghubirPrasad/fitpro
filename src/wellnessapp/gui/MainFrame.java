package wellnessapp.gui;

import wellnessapp.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * MainFrame - main application window
 * Demonstrates: GUI components, layout managers, event handling, Composite pattern
 */
public class MainFrame extends JFrame {
    private Dashboard dashboard;
    private FitnessPanel fitnessPanel;
    private MealPanel mealPanel;
    private HabitPanel habitPanel;
    private CalculatorPanel calculatorPanel;
    private JPanel currentPanel;
    
    public MainFrame(User user) {
        setTitle("Wellness App - " + user.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create panels
        dashboard = new Dashboard(user);
        fitnessPanel = new FitnessPanel(user);
        mealPanel = new MealPanel(user);
        habitPanel = new HabitPanel(user);
        calculatorPanel = new CalculatorPanel(user);
        
        // Navigation panel
        JPanel navPanel = new JPanel(new FlowLayout());
        JButton dashboardButton = new JButton("Dashboard");
        JButton fitnessButton = new JButton("Fitness");
        JButton mealButton = new JButton("Meals");
        JButton habitButton = new JButton("Habits");
        JButton calculatorButton = new JButton("Calculators");
        JButton logoutButton = new JButton("Logout");
        
        navPanel.add(dashboardButton);
        navPanel.add(fitnessButton);
        navPanel.add(mealButton);
        navPanel.add(habitButton);
        navPanel.add(calculatorButton);
        navPanel.add(logoutButton);
        
        // Event handlers for navigation
        dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(dashboard);
                dashboard.updateMetrics();
            }
        });
        
        fitnessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(fitnessPanel);
            }
        });
        
        mealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(mealPanel);
            }
        });
        
        habitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(habitPanel);
            }
        });
        
        calculatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(calculatorPanel);
            }
        });
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
        
        setLayout(new BorderLayout());
        add(navPanel, BorderLayout.NORTH);
        
        // Show dashboard by default
        showPanel(dashboard);
    }
    
    private void showPanel(JPanel panel) {
        if (currentPanel != null) {
            remove(currentPanel);
        }
        currentPanel = panel;
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}

