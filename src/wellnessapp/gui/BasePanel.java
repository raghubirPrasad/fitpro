package wellnessapp.gui;

import wellnessapp.models.User;
import wellnessapp.utils.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class BasePanel extends JPanel {
    protected User user;
    protected FileHandler fileHandler;
    protected float alpha = 0.0f;
    
    public BasePanel(User user) {
        this.user = user;
        this.fileHandler = FileHandler.getInstance();
        
        setLayout(new BorderLayout());
        setOpaque(true);
        
        startFadeInAnimation();
    }
    
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
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paintComponent(g2d);
        g2d.dispose();
    }
    
    protected abstract void loadData();
    
    protected abstract void updateDisplay();
}

