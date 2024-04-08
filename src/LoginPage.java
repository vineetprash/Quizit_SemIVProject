import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

class LoginPage extends JPanel {
    private App localApp;
    private Color bgColor = new Color(217,237,191);
    private Color accentColor = new Color(0, 102, 102);
    private Color fgColor = accentColor;
    private Color submitColor = Color.WHITE;
    
    // private File file = new File(App.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    // private String imagePath = file.getAbsolutePath();
    private String imagePath = "C:/Users/VineetPrashant/Documents/GitHub/Quizit_SemIVProject/src/Images/bg3.jpg";


    public LoginPage(App app) {
        System.out.println("LOGIN");
        this.localApp = app;
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(bgColor); 

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(bgColor); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(fgColor); 
        loginPanel.add(usernameLabel, gbc);

        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(fgColor); 
        loginPanel.add(passwordLabel, gbc);

        gbc.gridy++;
        JLabel userTypeLabel = new JLabel("You are a:");
        userTypeLabel.setForeground(fgColor); 
        loginPanel.add(userTypeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JTextField usernameField = new JTextField(15);
        usernameField.setBackground(bgColor); 
        usernameField.setForeground(Color.BLACK); 
        loginPanel.add(usernameField, gbc);

        gbc.gridy++;
        JPasswordField passField = new JPasswordField(15);
        passField.setBackground(bgColor); 
        passField.setForeground(Color.BLACK); 
        loginPanel.add(passField, gbc);

        gbc.gridy++;
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton isTeacher = new JRadioButton("Teacher");
        JRadioButton isStudent = new JRadioButton("Student");
        buttonGroup.add(isStudent);
        buttonGroup.add(isTeacher);
        isTeacher.setBackground(bgColor); 
        isStudent.setBackground(bgColor); 
        isTeacher.setForeground(fgColor); 
        isStudent.setForeground(fgColor); 
        JPanel userTypePanel = new JPanel(new GridLayout(1, 2));
        userTypePanel.setBackground(bgColor); 
        userTypePanel.setForeground(fgColor); 

        userTypePanel.add(isTeacher);
        userTypePanel.add(isStudent);
        loginPanel.add(userTypePanel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(accentColor); 
        loginButton.setForeground(submitColor); 

        loginPanel.add(loginButton, gbc);
        contentPanel.add(loginPanel, BorderLayout.EAST);

        JPanel imagePanel = new JPanel();
        BufferedImage myPicture = null;

        try {
            myPicture = ImageIO.read(new File(imagePath));
            ImageIcon imageIcon = new ImageIcon(myPicture);
            // ImageIcon imageIcon = new ImageIcon(getClass().getResource("src/Images/bg3.jpg"));
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
                        System.out.println("PRESSED");
                        new LandingPage(localApp);
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
        this.localApp.showPanel(contentPanel);
    }
}
