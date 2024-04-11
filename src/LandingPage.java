import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LandingPage extends JPanel {
    private App localApp;
    private BACKEND localBackend;
    private JButton newQuizButton;
    private JButton logoutButton;
    private JPanel testsPanel;
    private JLabel welcomeLabel;
    private JLabel pendingTestsLabel;

    private ArrayList<TestPanel> listOfTestPanels = new ArrayList<>();


    private Color bgColor = new Color(217,237,191);
    private Color accentColor = new Color(0, 102, 102);
    private Color darkColor = new Color(50, 50, 50);
    private Color lightColor = Color.WHITE;
    private Color submitColor = Color.WHITE;

    public LandingPage(App app, BACKEND backend) {
        this.localApp = app;
        this.localBackend = backend;
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(darkColor);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(darkColor);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 5));
        add(topPanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("QUIZIT");
        titleLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
        titleLabel.setForeground(lightColor);
        topPanel.add(titleLabel);

        newQuizButton = new JButton("New Quiz");
        newQuizButton.setBackground(accentColor);
        newQuizButton.setForeground(lightColor);
        topPanel.add(newQuizButton);

        logoutButton = new JButton("Logout");
        logoutButton.setBackground(accentColor);
        logoutButton.setForeground(lightColor);
        topPanel.add(logoutButton);
        
        testsPanel = new JPanel(new GridLayout(0, 3, 20, 20)); 
        testsPanel.setBackground(darkColor);
        mainPanel.add(testsPanel, BorderLayout.CENTER);

        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(darkColor);
        welcomePanel.setLayout(new BorderLayout());
        mainPanel.add(welcomePanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER); 

        welcomeLabel = new JLabel("Welcome, Vineet Prashant !");
        welcomeLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 24));
        welcomePanel.add(welcomeLabel, BorderLayout.NORTH);

        pendingTestsLabel = new JLabel("Pending Tests");
        pendingTestsLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
        welcomePanel.add(pendingTestsLabel, BorderLayout.CENTER);

        TestPanel test1 = new TestPanel("NNFL-IE2", "Deadline: 24/3/24, 12am", "Created by: Dr. XYZ");
        TestPanel test2 = new TestPanel("DBMS-Practise Test", "Deadline: 27/3/24, 7pm", "Created by: Dr. XYZ");
        TestPanel test3 = new TestPanel("PSE-Proficiency Test", "Deadline: 29/3/24, 12am", "Created by: Dr. XYZ");
        
        // Hardcoded currently, needs to be dynamic
        testsPanel.add(test1);
        testsPanel.add(test2);
        testsPanel.add(test3);

        newQuizButton.addActionListener(e -> {
            new NewQuizPanel(localApp);
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage(localApp, backend);
            }
        });

        localApp.showPanel(this);
    }

    private class TestPanel extends JPanel {
        public JLabel nameLabel = new JLabel();
        public JLabel deadlineLabel = new JLabel();
        public JLabel createdLabel = new JLabel();
        public JPanel infoPanel = new JPanel(new GridLayout(0, 1, 0, 5));
        public JButton attemptButton = new JButton("Attempt Test");

        public TestPanel(String testName, String deadline, String createdBy) {
            setLayout(new BorderLayout());
            setBackground(lightColor);
            setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Added border
            this.nameLabel.setText(testName);
            this.deadlineLabel.setText(deadline);
            this.createdLabel.setText(createdBy);
            add(nameLabel, BorderLayout.NORTH);

            infoPanel.setBackground(lightColor);
            add(infoPanel, BorderLayout.CENTER);

            infoPanel.add(deadlineLabel);
            infoPanel.add(createdLabel);

            attemptButton.setBackground(accentColor);
            attemptButton.setForeground(lightColor);
            infoPanel.add(attemptButton);
            listOfTestPanels.add(this);

            attemptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new QuizPanel(localApp, localBackend);
                }
            });
        }
    }
}
