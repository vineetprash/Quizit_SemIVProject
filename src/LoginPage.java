import javax.imageio.ImageIO;
import javax.swing.*;

import userAuth.UserAuthenticator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

class LoginPage extends JPanel{
    private App localApp;
    public LoginPage(App app) {
        this.localApp = app;
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.BLACK);
        
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.BLACK);
        loginPanel.add(usernameLabel, gbc);

        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.BLACK);
        loginPanel.add(passwordLabel, gbc);

        gbc.gridy++;
        JLabel userTypeLabel = new JLabel("You are a:");
        userTypeLabel.setForeground(Color.BLACK);
        loginPanel.add(userTypeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JTextField usernameField = new JTextField(15);
        usernameField.setBackground(Color.WHITE);
        usernameField.setForeground(Color.BLACK);
        loginPanel.add(usernameField, gbc);

        gbc.gridy++;
        JPasswordField passField = new JPasswordField(15);
        passField.setBackground(Color.WHITE);
        passField.setForeground(Color.BLACK);
        loginPanel.add(passField, gbc);

        gbc.gridy++;
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton isTeacher = new JRadioButton("Teacher");
        JRadioButton isStudent = new JRadioButton("Student");
        buttonGroup.add(isStudent);
        buttonGroup.add(isTeacher);
        isTeacher.setBackground(Color.WHITE);
        isStudent.setBackground(Color.WHITE);
        isTeacher.setForeground(Color.BLACK);
        isStudent.setForeground(Color.BLACK);
        JPanel userTypePanel = new JPanel(new GridLayout(1, 2));
        userTypePanel.setBackground(Color.WHITE);
        userTypePanel.setForeground(Color.BLACK);

        userTypePanel.add(isTeacher);
        userTypePanel.add(isStudent);
        loginPanel.add(userTypePanel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(Color.BLACK);
        
        loginPanel.add(loginButton, gbc);
        contentPanel.add(loginPanel, BorderLayout.EAST);


        JPanel imagePanel = new JPanel();
        BufferedImage myPicture = null;


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