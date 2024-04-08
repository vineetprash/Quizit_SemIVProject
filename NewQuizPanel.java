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

    private App localApp;
    public NewQuizPanel(App app) {
	this.localApp = app;
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Number of questions field
        JLabel numOfQuestionsLabel = new JLabel("Number of Questions:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(numOfQuestionsLabel, gbc);

        numOfQuestionsField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(numOfQuestionsField, gbc);

        // Question fields and option fields
        questionFields = new ArrayList<>();
        optionFields = new ArrayList<>();
        correctAnswerFields = new ArrayList<>();
        JButton submitButton = new JButton("Create Quiz");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createQuiz();
            }
        });
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);
	    localApp.showPanel(this);
    }

   private void createQuiz() {
        int numOfQuestions = Integer.parseInt(numOfQuestionsField.getText());
        new (addQuestionFields(numOfQuestions));
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

    private JPanel addQuestionFields(int numOfQuestions) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        JPanel questionFields = new JPanel();

        for (int i = 0; i < numOfQuestions; i++) {
            JLabel questionLabel = new JLabel("Question " + (i + 1) + ":");
            gbc.gridy++;
            questionFields.add(questionLabel, gbc);

            JTextField questionField = new JTextField(30);
            gbc.gridy++;
            add(questionField, gbc);
            questionFields.questionFields.add(questionField);

            JLabel optionsLabel = new JLabel("Options:");
            gbc.gridy++;
            questionFields.add(optionsLabel, gbc);

            ArrayList<JTextField> optionFieldsList = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                JTextField optionField = new JTextField(30);
                gbc.gridy++;
                add(optionField, gbc);
                optionFieldsList.add(optionField);
            }
            optionFields.add(optionFieldsList);
            questionFields.add(optionField);
            JLabel correctAnswerLabel = new JLabel("Correct Answer:");
            gbc.gridy++;
            questionFields.add(correctAnswerLabel, gbc);

            String[] options = {"Option 1", "Option 2", "Option 3", "Option 4"};
            JComboBox<String> correctAnswerComboBox = new JComboBox<>(options);
            gbc.gridy++;
            questionFields.add(correctAnswerComboBox, gbc);
            correctAnswerFields.add(correctAnswerComboBox);

        }

        gbc.gridy++;
        JLabel timeLimitLabel = new JLabel("Time Limit (in minutes):");
        gbc.gridy++;
        questionFields.add(timeLimitLabel, gbc);

        timeLimitField = new JTextField(10);
        gbc.gridy++;
        questionFields.add(timeLimitField, gbc);
        return questionFields;
    }



}
