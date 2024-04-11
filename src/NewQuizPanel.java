import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NewQuizPanel extends JPanel {
    
    private JTextField numOfQuestionsField;
    private ArrayList<JTextField> questionFields;
    private ArrayList<ArrayList<JTextField>> optionFields;
    private ArrayList<JComboBox<String>> correctAnswerFields;
    private JTextField timeLimitField; 
    private Color bgColor = new Color(217,237,191);
    private Color accentColor = new Color(0, 102, 102);
    private Color darkColor = new Color(50, 50, 50);
    private Color lightColor = Color.WHITE;
    private Color submitColor = Color.WHITE;
    private App localApp;
    private BACKEND localBackend;
    
    
    public NewQuizPanel(App app, BACKEND backend) {
        this.localApp = app;
        this.localBackend = backend;
        setLayout(new GridBagLayout());
        setBackground(darkColor);
        setForeground(accentColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;

        // Number of questions field
        JLabel numOfQuestionsLabel = new JLabel("Number of Questions:");
        numOfQuestionsLabel.setForeground(lightColor);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(numOfQuestionsLabel, gbc);

        numOfQuestionsField = new JTextField(10);
        numOfQuestionsField.setBackground(darkColor);
        numOfQuestionsField.setForeground(lightColor);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(numOfQuestionsField, gbc);

        // Question fields and option fields
        questionFields = new ArrayList<>();
        optionFields = new ArrayList<>();
        correctAnswerFields = new ArrayList<>();
        JButton submitButton = new JButton("Create Quiz");
        JButton backButton = new JButton("Back");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createQuiz();
            }
        });
        backButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Go back to home page? You will lose all progress. ", "Back", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                localApp.showPanel(new LandingPage(localApp, localBackend));
            }
        });
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backButton, gbc);


	    localApp.showPanel(this);
    }

   private void createQuiz() {
        int numOfQuestions = Integer.parseInt(numOfQuestionsField.getText());
        JFrame tempFrame = addQuestionFields(numOfQuestions, "Building Quiz");
        tempFrame.setVisible(true);
        tempFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // ArrayList<String> questions = new ArrayList<>();
        // ArrayList<ArrayList<String>> options = new ArrayList<>();
        // ArrayList<String> correctAnswers = new ArrayList<>();
        // for (int i = 0; i < numOfQuestions; i++) {
        //     questions.add(questionFields.get(i).getText());
        //     ArrayList<String> questionOptions = new ArrayList<>();
        //     for (JTextField optionField : optionFields.get(i)) {
        //         questionOptions.add(optionField.getText());
        //     }
        //     options.add(questionOptions);
        //     String correctAnswer = (String) correctAnswerFields.get(i).getSelectedItem();
        //     correctAnswers.add(correctAnswer);
        // }
        // int timeLimit = Integer.parseInt(timeLimitField.getText());
        // // Do something with the created quiz data
        // System.out.println("Quiz created with " + numOfQuestions + " questions and a time limit of " + timeLimit + " minutes.");
        // System.out.println("Questions: " + questions);
        // System.out.println("Options: " + options);
        // System.out.println("Correct Answers: " + correctAnswers);
    }

    private JFrame addQuestionFields(int numOfQuestions, String title) {
        JPanel questionFieldsPanel = new JPanel(new GridBagLayout());
        questionFieldsPanel.setBackground(darkColor);
        questionFieldsPanel.setForeground(lightColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
    
        for (int i = 0; i < numOfQuestions; i++) {
            JLabel questionLabel = new JLabel("Question " + (i + 1) + ":");
            questionLabel.setForeground(lightColor);
            questionFieldsPanel.add(questionLabel, gbc);

    
            gbc.gridy++;
            JTextField questionField = new JTextField(30);
            questionField.setForeground(lightColor);
            questionField.setBackground(darkColor);
            questionFieldsPanel.add(questionField, gbc);
    
            gbc.gridy++;
            JLabel optionsLabel = new JLabel("Options:");
            optionsLabel.setForeground(lightColor);
            optionsLabel.setBackground(darkColor);
            questionFieldsPanel.add(optionsLabel, gbc);
            
    
            ArrayList<JTextField> optionFieldsList = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                gbc.gridy++;
                JTextField optionField = new JTextField(30);
                optionField.setBackground(darkColor);
                optionField.setForeground(lightColor);
                questionFieldsPanel.add(optionField, gbc);
                optionFieldsList.add(optionField);
            }
    
            gbc.gridy++;
            JLabel correctAnswerLabel = new JLabel("Correct Answer:");
            questionFieldsPanel.add(correctAnswerLabel, gbc);
    
            gbc.gridy++;
            String[] options = {"Option 1", "Option 2", "Option 3", "Option 4"};
            JComboBox<String> correctAnswerComboBox = new JComboBox<>(options);
            questionFieldsPanel.add(correctAnswerComboBox, gbc);
            correctAnswerFields.add(correctAnswerComboBox);
    
            gbc.gridy++;
            gbc.insets = new Insets(20, 0, 0, 0); // Add some space between questions
        }
    
        gbc.gridy++;
        gbc.insets = new Insets(5, 5, 5, 5);
        JLabel timeLimitLabel = new JLabel("Time Limit (in minutes):");
        questionFieldsPanel.add(timeLimitLabel, gbc);
    
        gbc.gridy++;
        JTextField timeLimitField = new JTextField(10);
        questionFieldsPanel.add(timeLimitField, gbc);

        JScrollPane scrollPane = new JScrollPane(questionFieldsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JFrame frame = new JFrame(title);
        frame.add(scrollPane);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
     
        return frame;
    }
    



}
