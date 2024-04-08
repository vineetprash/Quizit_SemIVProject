import javax.swing.*;
import java.awt.*;


public class App extends JFrame {

    public App() {
        setTitle("Quizit");
        setSize(800, 450);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center align the JFrame
       
        new LoginPage(this);
        setVisible(true);
    }

    public void showPanel(JPanel panel) {
        // Remove all components from the frame
        getContentPane().removeAll();

        // Add the specified panel to the frame
        add(panel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
    public void addPanelOnTop(JPanel panel) {
 
        // Add the specified panel to the frame
        add(panel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new App();
            }
        });
    }
}


