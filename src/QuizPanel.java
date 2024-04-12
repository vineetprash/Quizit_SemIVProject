// quizDetails(quizId, quizName, timeLimit, scoringCriteria)
// questionsData(question_id, question_text, option1, option2, option3, option4, correct_answer)
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuizPanel extends JPanel {
    private Object[] quizDetails;
    private ArrayList<Question> questions = new ArrayList<>();
    private Map<Integer, String> userResponses = new HashMap<>();
    private int currentQuestionIndex;
    private JLabel questionLabel;
    private JPanel optionsPanel;
    private JButton prevButton;
    private JButton nextButton;
    private JButton exitButton;
    private JPanel questionNumberPanel;
    private Timer timer;
    private JLabel timerLabel;
    private int timeLimit;
    private int score = 0;
    private JLabel scoreLabel; // New label to display score

    private App localApp;
    private BACKEND localBackend;
    private String quizName;

    public QuizPanel(App app, BACKEND backend, String quizName) throws SQLException {
        this.localApp = app;
        this.localBackend = backend;
        this.quizName = quizName;
        System.out.println("This is quiz panel !!!!");

        // Fetch quiz details from backend
        quizDetails = localBackend.getQuizDetails(quizName);
        if (quizDetails != null && quizDetails.length == 4) {
            // Extract quiz details
            int quizId = (int) quizDetails[0];
            this.quizName = (String) quizDetails[1];
            this.timeLimit = (int) quizDetails[2];
            // Fetch questions for the quiz from backend

            Object[][] questionsData = localBackend.fetchQuestionsByQuizId(quizId);
            System.out.println(questionsData.length + "Hellllllll yeaaaaaaaaaaa");
            for (int i = 0; i < questionsData.length; i++) {
                System.out.print("[ ");
                for (int j = 0; j < questionsData[i].length; j++) {
                    System.out.print(questionsData[i][j] + " ");
                }
                System.out.println("]");

            }
            System.out.println(questionsData);
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
                    System.out.println(questionData[6] + "This is data");
                    int correctOption = Integer.parseInt((String)questionData[6]);
                    questions.add(new Question(questionText, options, (String) questionData[correctOption + 2]));
                    System.out.println(correctOption + "this is correct option");
                }
            }
        }

        // Initialize user responses map with default values
        for (int i = 0; i < questions.size(); i++) {
            userResponses.put(i, "NA");
        }

        this.currentQuestionIndex = 0;

        setLayout(new BorderLayout());
        setBackground(new Color(50, 50, 50));

        // Timer setup
        timerLabel = new JLabel();
        timerLabel.setBackground(new Color(50, 50, 50));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setOpaque(true);
        timerLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        // Add timer label to top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(50, 50, 50));
        topPanel.add(timerLabel, BorderLayout.CENTER);
        // Add score label to top panel
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        topPanel.add(scoreLabel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

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
        exitButton.addActionListener(e -> {
            try {
                exitQuiz();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        // Show first question
        showQuestion(0);

        // Start timer with time limit
        startTimer(timeLimit);

        // Show panel in the application window
        localApp.showPanel(this);
    }

    private void startTimer(int timeLimit) {
        timer = new Timer(1000, new ActionListener() {
            int seconds = timeLimit * 60;

            @Override
            public void actionPerformed(ActionEvent e) {
                seconds--;
                if (seconds == 0) {
                    timerLabel.setText("Timer: 00:00");
                    try {
                        submitQuiz();
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } // Submit quiz when timer reaches zero
                    timer.stop();
                } else {
                    int minutes = seconds / 60;
                    int remainingSeconds = seconds % 60;
                    timerLabel.setText(String.format("Timer: %02d:%02d", minutes, remainingSeconds));
                }
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
                userResponses.put(index, selectedOption); // Update user response in the map
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

    private void exitQuiz() throws SQLException {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to submit the quiz and exit? ", "Exit Quiz", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            submitQuiz();
            localApp.showPanel(new LandingPage(localApp, localBackend));
        }
    }

    private void submitQuiz() throws SQLException {
        timer.stop(); // Stop the timer
        score = 0; // Reset score
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String userResponse = userResponses.get(i);
            if (userResponse != null && userResponse.equals(question.getCorrectAnswer())) {
                score++;
            }
        }
        // Display score in the score label
        // scoreLabel.setText("Score: " + score);
        localBackend.storeStudentScore(localApp.sessionUser, score,(int)quizDetails[0]);
    }

    private class Question {
        private String text;
        private ArrayList<String> options;
        private String correctAnswer;

        public Question(String text, ArrayList<String> options, String correctAnswer) {
            this.text = text;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }

        public String getText() {
            return text;
        }

        public ArrayList<String> getOptions() {
            return options;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }
}
