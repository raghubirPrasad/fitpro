package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.utils.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * BasePanel - Abstract base class for all panels
 * Demonstrates: Abstract classes, inheritance, code reuse
 * 
 * Provides common functionality for all panels:
 * - User and FileHandler initialization
 * - Fade-in animation
 * - Common panel setup
 */
public abstract class BasePanel extends JPanel {
    protected User user;
    protected FileHandler fileHandler;
    protected float alpha = 0.0f; // For fade-in effect
    
    /**
     * Constructor for BasePanel
     * @param user The user associated with this panel
     */
    public BasePanel(User user) {
        this.user = user;
        this.fileHandler = FileHandler.getInstance();
        
        setLayout(new BorderLayout());
        setOpaque(true);
        
        // Start fade-in animation
        startFadeInAnimation();
    }
    
    /**
     * Starts the fade-in animation effect
     * This method is common to all panels
     */
    protected void startFadeInAnimation() {
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
    
    /**
     * Paints the component with fade-in effect
     * This method is common to all panels
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paintComponent(g2d);
        g2d.dispose();
    }
    
    /**
     * Abstract method to load data specific to each panel
     * Must be implemented by subclasses
     */
    protected abstract void loadData();
    
    /**
     * Abstract method to update display specific to each panel
     * Must be implemented by subclasses
     */
    protected abstract void updateDisplay();
}

