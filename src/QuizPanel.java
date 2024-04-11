import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class QuizPanel extends JPanel {
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<String> userResponses;
    private int currentQuestionIndex;
    private JLabel questionLabel;
    private JPanel optionsPanel;
    private JButton prevButton;
    private JButton nextButton;
    private JButton exitButton; // New exit button
    private JPanel questionNumberPanel;
    private Timer timer;
    private JLabel timerLabel;

    private App localApp;
    private BACKEND localBackend;

    public QuizPanel(App app, BACKEND backend) {
        this.localApp = app;
        this.localBackend = backend;
        // DUMMY DATA 1
        ArrayList<String> options1 = new ArrayList<>();
        options1.add("Option 1 for Question 1");
        options1.add("Option 2 for Question 1");
        options1.add("Option 3 for Question 1");
        questions.add(new Question("Question 1?", options1));
        
        // DUMMY DATA 2
        ArrayList<String> options2 = new ArrayList<>();
        options2.add("Option A for Question 2");
        options2.add("Option B for Question 2");
        options2.add("Option C for Question 2");
        questions.add(new Question("Question 2?", options2));

        userResponses = new ArrayList<>(questions.size());
        for (int i = 0; i < questions.size(); i++) {
            userResponses.add("NA");
        }

        this.currentQuestionIndex = 0;

        setLayout(new BorderLayout());
        setBackground(new Color(50,50,50));


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(50, 50, 50));

        // Timer setup
        timerLabel = new JLabel("Timer: 00:00");
        timerLabel.setBackground(new Color(50, 50, 50));
        timerLabel.setForeground(Color.WHITE); 
        timerLabel.setOpaque(true); 
        timerLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); 
        topPanel.add(timerLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        startTimer();


        JPanel mainPanel = new JPanel(new BorderLayout());

        questionLabel = new JLabel();
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setForeground(new Color(0, 102, 102)); 
        mainPanel.add(questionLabel, BorderLayout.NORTH); 
   
        optionsPanel = new JPanel(new GridLayout(0, 1));
        mainPanel.add(optionsPanel, BorderLayout.CENTER); 
     
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        exitButton = new JButton("Exit Quiz"); // Initialize exit button
        prevButton.setBackground(new Color(0, 102, 102));
        nextButton.setBackground(new Color(0, 102, 102)); 
        exitButton.setBackground(new Color(0, 102, 102)); // Set exit button color
        prevButton.setForeground(Color.WHITE); 
        nextButton.setForeground(Color.WHITE); 
        exitButton.setForeground(Color.WHITE); // Set exit button text color
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(exitButton); // Add exit button to the panel
        buttonPanel.setBackground(new Color(240, 240, 240)); 
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);


        questionNumberPanel = new JPanel(new GridLayout(0, 1));
        mainPanel.add(questionNumberPanel, BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);


        prevButton.addActionListener(e -> showPreviousQuestion());
        nextButton.addActionListener(e -> showNextQuestion());
        exitButton.addActionListener(e -> exitQuiz()); // Add action listener to exit button


        showQuestion(0);

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
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit the quiz? You will loose all progress.", "Exit Quiz", JOptionPane.YES_NO_OPTION);
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
