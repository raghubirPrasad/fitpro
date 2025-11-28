package wellnessapp.main;

import wellnessapp.gui.LoginFrame;

import javax.swing.SwingUtilities;

/**
 * Main class to start the Wellness App
 * Demonstrates: Main method, program entry point
 */
public class Main {
    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater for thread-safe GUI initialization
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });
    }
}

