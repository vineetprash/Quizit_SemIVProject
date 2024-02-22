import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Custom packages. Each package is a submodule.
import userAuth.UserAuthenticator;

// Main class for the Swing application
public class App extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public App() {
        setTitle("User Authentication");
        setSize(300, 200);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center align the JFrame

        JPanel loginPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        JTextField usernameField = new JTextField();

        JPasswordField passField = new JPasswordField();
        loginPanel.setLayout(new GridLayout(4, 3));
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passField);
        loginPanel.add(new JLabel()); // Placeholder
        loginPanel.add(loginButton);

        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new GridLayout(3, 2));
        dashboardPanel.add(new JLabel("Welcome to QUIZIT!"));

        JPanel[] routes = { loginPanel, dashboardPanel };

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passField.getPassword());

                // Authenticate user
                boolean isAuthenticated = UserAuthenticator.authenticateLogin(username, password);

                if (isAuthenticated) {
                    showPanel(dashboardPanel);
                } else {
                    JOptionPane.showMessageDialog(App.this,
                            "Authentication failed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(loginPanel);
        setVisible(true);
    }

    private void showPanel(JPanel panel) {
        // Remove all components from the frame
        getContentPane().removeAll();

        // Add the specified panel to the frame
        add(panel, BorderLayout.CENTER);

        // Revalidate and repaint the frame to reflect changes
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
