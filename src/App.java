
import javax.imageio.ImageIO;
import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Custom packages for logic of each submodule.
import userAuth.UserAuthenticator;
public class App extends JFrame {


    
    public App() {
        setTitle("Quizit");
        setSize(800, 450);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center align the JFrame
        String[] routes = { "Landing", "Login"};
        // showPanel(new LoginPage(this));
        new LoginPage(this);
        setVisible(true);
    }


    public void showPanel(JPanel panel) {
        // Remove all components from the frame
        getContentPane().removeAll();

        // Add the specified panel to the frame
        add(panel, BorderLayout.CENTER);

        //  Revalidate and repaint the frame to reflect changes
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
        initComponents(app);
    }
                        
    private void initComponents(App app) {
        setLayout(new BorderLayout());
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        test4 = new javax.swing.JPanel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        test5 = new javax.swing.JPanel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        test6 = new javax.swing.JPanel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();




        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(20, 100, 5);
        flowLayout1.setAlignOnBaseline(true);
        jPanel1.setLayout(flowLayout1);

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); 
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("QUIZIT");
        jPanel1.add(jLabel12);

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("New Quiz");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton2.setBackground(new java.awt.Color(0, 102, 102));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Logout");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // TODO : [Logout]
                app.showPanel(new LoginPage(app));
            }
        });
        jPanel1.add(jButton2);

       

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setFont(new java.awt.Font("Yu Gothic Light", 0, 12)); 

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.FlowLayout(1, 50, 5));

        jLabel13.setText("NNFL-IE2");

        jLabel14.setText("Deadline: 24/3/24, 12am");

        jLabel15.setText("Created by: Dr. XYZ");

        jButton5.setBackground(new java.awt.Color(0, 102, 102));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Attempt Test");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout test4Layout = new javax.swing.GroupLayout(test4);
        test4.setLayout(test4Layout);
        test4Layout.setHorizontalGroup(
            test4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, test4Layout.createSequentialGroup()
                .addGap(0, 21, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(18, 18, 18))
            .addGroup(test4Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, test4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(test4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addComponent(jLabel15))
                .addGap(31, 31, 31))
        );
        test4Layout.setVerticalGroup(
            test4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(test4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel3.add(test4);

        jLabel16.setText("DBMS-Practise Test");

        jLabel17.setText("Deadline: 27/3/24, 7pm");

        jLabel18.setText("Created by: Dr. XYZ");

        jButton6.setBackground(new java.awt.Color(0, 102, 102));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Attempt Test");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout test5Layout = new javax.swing.GroupLayout(test5);
        test5.setLayout(test5Layout);
        test5Layout.setHorizontalGroup(
            test5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, test5Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(test5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, test5Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, test5Layout.createSequentialGroup()
                        .addGroup(test5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addGroup(test5Layout.createSequentialGroup()
                                .addGroup(test5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18)
                                    .addComponent(jButton6))
                                .addGap(4, 4, 4)))
                        .addGap(31, 31, 31))))
        );
        test5Layout.setVerticalGroup(
            test5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(test5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel3.add(test5);

        jLabel19.setText("PSE-Proficiency Test");

        jLabel20.setText("Deadline: 29/3/24, 12am");

        jLabel21.setText("Created by: Dr. XYZ");

        jButton7.setBackground(new java.awt.Color(0, 102, 102));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Attempt Test");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout test6Layout = new javax.swing.GroupLayout(test6);
        test6.setLayout(test6Layout);
        test6Layout.setHorizontalGroup(
            test6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, test6Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(test6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, test6Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, test6Layout.createSequentialGroup()
                        .addGroup(test6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7)
                            .addComponent(jLabel21))
                        .addGap(31, 31, 31))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, test6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(18, 18, 18))
        );
        test6Layout.setVerticalGroup(
            test6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(test6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addComponent(jButton7)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel3.add(test6);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); 
        jLabel2.setText("Pending Tests");

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 24)); 
        jLabel1.setText("Welcome, Vineet Prashant !");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(408, 408, 408))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        // JPanel contentPanel = new javax.swing.JPanel();
        setLayout(new BorderLayout());
        jPanel2.add(jPanel4);
        jPanel2.add(jPanel3);
        add(jPanel1, BorderLayout.NORTH);
        add(jPanel2, BorderLayout.CENTER);

        app.showPanel(this);
    }                    

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                  
     

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                      

  
    

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JPanel test4;
    private javax.swing.JPanel test5;
    private javax.swing.JPanel test6;
    // End of variables declaration                   


       
}

    




