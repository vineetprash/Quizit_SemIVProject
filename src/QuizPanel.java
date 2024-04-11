import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuizPanel extends JPanel {
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<String> userResponses;
    private int currentQuestionIndex;
    private JLabel questionLabel;
    private JPanel optionsPanel;
    private JButton prevButton;
    private JButton nextButton;
    private JButton exitButton;
    private JPanel questionNumberPanel;
    private Timer timer;
    private JLabel timerLabel;

    private App localApp;
    private BACKEND localBackend;
    private String quizName;
    private int timeLimit;

    public QuizPanel(App app, BACKEND backend, String quizName) throws SQLException {
        this.localApp = app;
        this.localBackend = backend;
        this.quizName = quizName;

        // Fetch quiz details from backend
        Object[] quizDetails = localBackend.getQuizDetails(quizName);
        if (quizDetails != null && quizDetails.length == 4) {
            // Extract quiz details
            int quizId = (int) quizDetails[0];
            this.quizName = (String) quizDetails[1];
            this.timeLimit = (int) quizDetails[2];
            // Fetch questions for the quiz from backend
            Object[][] questionsData = localBackend.fetchQuestionsByQuizId(quizId);
            if (questionsData != null) {
                // Populate questions array list
                for (Object[] questionData : questionsData) {
                    String questionText = (String) questionData[1];
                    ArrayList<String> options = new ArrayList<>();
                    for (int i = 2; i <= 5; i++) {
                        if (questionData[i] != null) {
                            options.add((String) questionData[i]);
                        }
                    }
                    questions.add(new Question(questionText, options));
                }
            }
        }

        userResponses = new ArrayList<>(questions.size());
        for (int i = 0; i < questions.size(); i++) {
            userResponses.add("NA");
        }

        this.currentQuestionIndex = 0;

        setLayout(new BorderLayout());
        setBackground(new Color(50, 50, 50));

        // Timer setup
        timerLabel = new JLabel("Timer: 00:00");
        timerLabel.setBackground(new Color(50, 50, 50));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setOpaque(true);
        timerLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        // Add timer label to top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(50, 50, 50));
        topPanel.add(timerLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        startTimer();

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Add question label to main panel
        questionLabel = new JLabel();
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setForeground(new Color(0, 102, 102));
        mainPanel.add(questionLabel, BorderLayout.NORTH);

        optionsPanel = new JPanel(new GridLayout(0, 1));
        mainPanel.add(optionsPanel, BorderLayout.CENTER);

        // Add buttons to button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        exitButton = new JButton("Exit Quiz");
        prevButton.setBackground(new Color(0, 102, 102));
        nextButton.setBackground(new Color(0, 102, 102));
        exitButton.setBackground(new Color(0, 102, 102));
        prevButton.setForeground(Color.WHITE);
        nextButton.setForeground(Color.WHITE);
        exitButton.setForeground(Color.WHITE);
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(exitButton);
        buttonPanel.setBackground(new Color(240, 240, 240));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        questionNumberPanel = new JPanel(new GridLayout(0, 1));
        mainPanel.add(questionNumberPanel, BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);

        // Add action listeners to buttons
        prevButton.addActionListener(e -> showPreviousQuestion());
        nextButton.addActionListener(e -> showNextQuestion());
        exitButton.addActionListener(e -> exitQuiz());

        // Show first question
        showQuestion(0);

        // Show panel in the application window
        localApp.showPanel(this);
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            int seconds = 0;
            int minutes = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                if (seconds == 60) {
                    minutes++;
                    seconds = 0;
                }
                timerLabel.setText(String.format("Timer: %02d:%02d", minutes, seconds));
            }
        });
        timer.start();
    }

    private void showQuestion(int index) {
        Question question = questions.get(index);
        questionLabel.setText(question.getText());
        optionsPanel.removeAll();

        ButtonGroup buttonGroup = new ButtonGroup(); // Group to ensure only one option is selected at a time

        for (String option : question.getOptions()) {
            JRadioButton radioButton = new JRadioButton(option);
            radioButton.addActionListener(e -> {
                String selectedOption = ((JRadioButton) e.getSource()).getText();
                userResponses.set(index, selectedOption);
                System.out.println(userResponses);
            });
            optionsPanel.add(radioButton);
            buttonGroup.add(radioButton);

        }
        validate();
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            showQuestion(currentQuestionIndex);
        }
    }

    private void showPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            showQuestion(currentQuestionIndex);
        }
    }

    private void exitQuiz() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit the quiz? You will lose all progress.", "Exit Quiz", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            localApp.showPanel(new LandingPage(localApp, localBackend));
        }
    }

    private class Question {
        private String text;
        private ArrayList<String> options;

        public Question(String text, ArrayList<String> options) {
            this.text = text;
            this.options = options;
        }

        public String getText() {
            return text;
        }

        public ArrayList<String> getOptions() {
            return options;
        }
    }
}
