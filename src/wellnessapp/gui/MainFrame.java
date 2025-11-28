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
        
        // Navigation panel with animated buttons and subtle gradient
        JPanel navPanel = new JPanel(new FlowLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Subtle gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(248, 248, 248),
                    0, getHeight(), new Color(240, 240, 240)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Subtle bottom border
                g2d.setColor(new Color(200, 200, 200));
                g2d.setStroke(new BasicStroke(1.0f));
                g2d.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
                
                g2d.dispose();
            }
        };
        navPanel.setOpaque(false);
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        wellnessapp.utils.AnimatedButton dashboardButton = new wellnessapp.utils.AnimatedButton("Dashboard");
        wellnessapp.utils.AnimatedButton fitnessButton = new wellnessapp.utils.AnimatedButton("Fitness");
        wellnessapp.utils.AnimatedButton mealButton = new wellnessapp.utils.AnimatedButton("Meals");
        wellnessapp.utils.AnimatedButton habitButton = new wellnessapp.utils.AnimatedButton("Habits");
        wellnessapp.utils.AnimatedButton calculatorButton = new wellnessapp.utils.AnimatedButton("Calculators");
        wellnessapp.utils.AnimatedButton logoutButton = new wellnessapp.utils.AnimatedButton("Logout");
        logoutButton.setButtonColors(new Color(244, 67, 54), new Color(211, 47, 47), new Color(198, 40, 40));
        
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

