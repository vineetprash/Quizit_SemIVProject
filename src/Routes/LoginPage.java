// To be implemented later

package Routes;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import userAuth.UserAuthenticator;


public class LoginPage  {
    
    public LoginPage() {

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
        
        // loginButton.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         String username = usernameField.getText();
        //         String password = new String(passField.getPassword());

        //         // Authenticate user
        //         boolean isAuthenticated = UserAuthenticator.authenticateLogin(username, password);

        //         if (isAuthenticated) {
        //             app.showPanel(LandingPage.panel());
        //         } else {
        //             JOptionPane.showMessageDialog(App.this,
        //                     "Authentication failed", "Error", JOptionPane.ERROR_MESSAGE);
        //         }
        //     }
        // });

        
    }
}
