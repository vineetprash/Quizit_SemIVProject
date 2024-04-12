import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LandingPage extends JPanel {
    private App localApp;
    private BACKEND localBackend;
    private JButton newQuizButton;
    private JButton logoutButton;
    private JButton viewResultsButton;
    private JPanel testsPanel;
    private JLabel welcomeLabel;
    private JLabel pendingTestsLabel;

    private ArrayList<TestPanel> listOfTestPanels = new ArrayList<>();
    private List<Object[]> uniqueQuizzes;

    private Color bgColor = new Color(217, 237, 191);
    private Color accentColor = new Color(0, 102, 102);
    private Color darkColor = new Color(50, 50, 50);
    private Color lightColor = Color.WHITE;
    private Color submitColor = Color.WHITE;

    public LandingPage(App app, BACKEND backend) {
        this.localApp = app;
        this.localBackend = backend;
        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(lightColor);
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
        newQuizButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 10));
        topPanel.add(newQuizButton);

        viewResultsButton = new JButton("View Results");
        viewResultsButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 10));
        viewResultsButton.setBackground(accentColor);
        viewResultsButton.setForeground(lightColor);
        topPanel.add(viewResultsButton);

        logoutButton = new JButton("Logout");
        logoutButton.setBackground(accentColor);
        logoutButton.setForeground(lightColor);
        logoutButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 10));
        topPanel.add(logoutButton);

        testsPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        testsPanel.setBackground(lightColor);
        mainPanel.add(testsPanel, BorderLayout.CENTER);

        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(lightColor);
        welcomePanel.setLayout(new BorderLayout());
        mainPanel.add(welcomePanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        welcomeLabel = new JLabel("Welcome, " + localApp.sessionUser + " !");
        welcomeLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 24));
        welcomePanel.add(welcomeLabel, BorderLayout.NORTH);

        pendingTestsLabel = new JLabel("Pending Tests");
        pendingTestsLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
        welcomePanel.add(pendingTestsLabel, BorderLayout.CENTER);

        try {
            // uniqueQuizzes = {quizId, quizName}
            uniqueQuizzes = localBackend.getQuizzes();
            for (Object[] quiz : uniqueQuizzes) {
                // int quizId = (int) quiz[0];
                String quizName = (String) quiz[1];
                TestPanel testPanel = new TestPanel(quizName, "Subtitle", "Dummy User");
                testsPanel.add(testPanel);
                listOfTestPanels.add(testPanel);
            }
        } catch (Exception e) {
            System.out.println("An error occured in fetching tests");
            e.printStackTrace();
        }

        newQuizButton.addActionListener(e -> {
            new NewQuizPanel(localApp, localBackend);
        });

        logoutButton.addActionListener(e -> {
            new LoginPage(localApp, localBackend);
        });

        viewResultsButton.addActionListener(e -> {
            System.out.println("BUTTON KIA PRESSS");
            Object[][] data = localBackend.viewScores(localApp.sessionUser);
            new ResultFrame(data);
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
            setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
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
                    try {
                        System.out.println("KUCH HUA");
                        new QuizPanel(localApp, localBackend, "MCQ");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
    }
}
