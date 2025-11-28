package wellnessapp.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * AnimatedButton - Enhanced button with hover and click animations
 * Demonstrates: Custom components, mouse events, animations
 */
public class AnimatedButton extends JButton {
    private Color normalColor;
    private Color hoverColor;
    private Color clickColor;
    private Timer hoverTimer;
    private float hoverAlpha = 0.0f;
    private boolean isHovering = false;
    
    public AnimatedButton(String text) {
        super(text);
        this.normalColor = new Color(100, 150, 255);
        this.hoverColor = new Color(70, 130, 230);
        this.clickColor = new Color(50, 110, 200);
        
        setForeground(Color.WHITE);
        setBackground(normalColor);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false); // For custom painting with shadows
        setContentAreaFilled(false);
        
        // Add subtle border
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        // Add hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovering = true;
                startHoverAnimation(true);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                isHovering = false;
                startHoverAnimation(false);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(clickColor);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (isHovering) {
                    setBackground(hoverColor);
                } else {
                    setBackground(normalColor);
                }
            }
        });
    }
    
    private void startHoverAnimation(boolean enter) {
        if (hoverTimer != null && hoverTimer.isRunning()) {
            hoverTimer.stop();
        }
        
        hoverTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (enter) {
                    hoverAlpha += 0.1f;
                    if (hoverAlpha >= 1.0f) {
                        hoverAlpha = 1.0f;
                        hoverTimer.stop();
                    }
                } else {
                    hoverAlpha -= 0.1f;
                    if (hoverAlpha <= 0.0f) {
                        hoverAlpha = 0.0f;
                        hoverTimer.stop();
                    }
                }
                
                // Interpolate between normal and hover colors
                int r = (int) (normalColor.getRed() + (hoverColor.getRed() - normalColor.getRed()) * hoverAlpha);
                int g = (int) (normalColor.getGreen() + (hoverColor.getGreen() - normalColor.getGreen()) * hoverAlpha);
                int b = (int) (normalColor.getBlue() + (hoverColor.getBlue() - normalColor.getBlue()) * hoverAlpha);
                
                repaint();
            }
        });
        hoverTimer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Interpolate color based on hover state
        int red = (int) (normalColor.getRed() + (hoverColor.getRed() - normalColor.getRed()) * hoverAlpha);
        int green = (int) (normalColor.getGreen() + (hoverColor.getGreen() - normalColor.getGreen()) * hoverAlpha);
        int blue = (int) (normalColor.getBlue() + (hoverColor.getBlue() - normalColor.getBlue()) * hoverAlpha);
        Color currentColor = new Color(red, green, blue);
        
        // Draw subtle gradient
        GradientPaint gradient = new GradientPaint(
            0, 0, currentColor.brighter(),
            0, getHeight(), currentColor.darker()
        );
        g2d.setPaint(gradient);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
        
        // Subtle shadow effect
        if (hoverAlpha > 0.3f) {
            g2d.setColor(new Color(0, 0, 0, (int)(30 * hoverAlpha)));
            g2d.fillRoundRect(2, 2, getWidth(), getHeight(), 8, 8);
            g2d.setPaint(gradient);
            g2d.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 8, 8);
        }
        
        // Draw text with subtle shadow
        g2d.setColor(new Color(0, 0, 0, 50));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = (getWidth() - fm.stringWidth(getText())) / 2;
        int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2d.drawString(getText(), textX + 1, textY + 1);
        
        g2d.setColor(Color.WHITE);
        g2d.drawString(getText(), textX, textY);
        
        g2d.dispose();
    }
    
    public void setButtonColors(Color normal, Color hover, Color click) {
        this.normalColor = normal;
        this.hoverColor = hover;
        this.clickColor = click;
        repaint();
    }
}

