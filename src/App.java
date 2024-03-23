
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Custom packages for logic og each submodule.
import userAuth.UserAuthenticator;
public class App extends JFrame {


    
    public App() {
        setTitle("Quizit");
        setSize(800, 450);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center align the JFrame
        String[] routes = { "Landing", "Login"};
        new LoginPage(this);
        setVisible(true);
    }


    public void showPanel(JPanel panel) {
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


class LoginPage extends JPanel{
    

        public LoginPage(App app) {
           
            

            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBackground(Color.BLACK);
            
            JPanel loginPanel = new JPanel(new GridBagLayout());
            loginPanel.setBackground(Color.DARK_GRAY);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.LINE_END;

            JLabel usernameLabel = new JLabel("Username:");
            usernameLabel.setForeground(Color.WHITE);
            loginPanel.add(usernameLabel, gbc);

            gbc.gridy++;
            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setForeground(Color.WHITE);
            loginPanel.add(passwordLabel, gbc);

            gbc.gridy++;
            JLabel userTypeLabel = new JLabel("You are a:");
            userTypeLabel.setForeground(Color.WHITE);
            loginPanel.add(userTypeLabel, gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;

            JTextField usernameField = new JTextField(15);
            usernameField.setBackground(Color.DARK_GRAY);
            usernameField.setForeground(Color.WHITE);
            loginPanel.add(usernameField, gbc);

            gbc.gridy++;
            JPasswordField passField = new JPasswordField(15);
            passField.setBackground(Color.DARK_GRAY);
            passField.setForeground(Color.WHITE);
            loginPanel.add(passField, gbc);

            gbc.gridy++;
            ButtonGroup buttonGroup = new ButtonGroup();
            JRadioButton isTeacher = new JRadioButton("Teacher");
            JRadioButton isStudent = new JRadioButton("Student");
            buttonGroup.add(isStudent);
            buttonGroup.add(isTeacher);
            isTeacher.setBackground(Color.DARK_GRAY);
            isStudent.setBackground(Color.DARK_GRAY);
            isTeacher.setForeground(Color.WHITE);
            isStudent.setForeground(Color.WHITE);
            JPanel userTypePanel = new JPanel(new GridLayout(1, 2));
            userTypePanel.setBackground(Color.DARK_GRAY);
            userTypePanel.setForeground(Color.WHITE);

            userTypePanel.add(isTeacher);
            userTypePanel.add(isStudent);
            loginPanel.add(userTypePanel, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.NONE;
            JButton loginButton = new JButton("Login");
            loginButton.setBackground(Color.DARK_GRAY);
            loginButton.setForeground(Color.WHITE);
            
            loginPanel.add(loginButton, gbc);
            contentPanel.add(loginPanel, BorderLayout.EAST);


            JPanel imagePanel = new JPanel();
            BufferedImage myPicture = null;

            // ImageIcon imageIcon = new ImageIcon("src/Images/bg1.jpg");
            // Image image = imageIcon.getImage().getScaledInstance(1600, 900, Image.SCALE_SMOOTH);
            // ImageIcon scaledImageIcon = new ImageIcon(image);
            // JLabel imageLabel = new JLabel(scaledImageIcon);
            // imagePanel.add(imageLabel);
            // contentPanel.add(imageLabel, BorderLayout.WEST);
           

            try {
                myPicture = ImageIO.read(new File("C:/Users/VineetPrashant/Documents/GitHub/Quizit_SemIVProject/src/Images/bg1.jpg"));
                ImageIcon imageIcon = new ImageIcon(myPicture);
                Image image = imageIcon.getImage().getScaledInstance(1000, 900, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(image));
                imageLabel.setLayout(new BorderLayout());
                imagePanel.add(imageLabel, BorderLayout.CENTER);
                contentPanel.add(imageLabel, BorderLayout.CENTER);
            } catch (IOException e1) {
                
                e1.printStackTrace();
                contentPanel.add(new JPanel(), BorderLayout.WEST);
                System.out.println("Error caught, couldn't load image");
            }


            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText();
                    String password = new String(passField.getPassword());

                    // Authenticate user
                    if (username.length() > 0 && password.length() > 0) {
                        boolean isAuthenticated = UserAuthenticator.authenticateLogin(username, password);
                        if (isAuthenticated) {
                            new LandingPage(app);
                        } else {
                            JOptionPane.showMessageDialog(app,
                                    "Username and Password do not match", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(app,
                                "Please fill out all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            
            
            
    
        
            app.showPanel(contentPanel);
        }
    }


class LandingPage extends JPanel{
    public LandingPage(App app) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Welcome to QUIZIT LOLLLL!"));


        JButton logoutButton = new JButton("Logout");
        panel.add(logoutButton);


        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage(app);
            }
        });

        app.showPanel(panel);

    }
}