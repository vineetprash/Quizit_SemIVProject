import java.awt.*;
import javax.swing.*;


public class QuizCreatorWindow extends JFrame {


    public QuizCreatorWindow() {
        
    }


   private void addQuestionFields(int numOfQuestions) {
    questionLabel = new JLabel("Question " + (i + 1) + ":");
        gbc.gridy++;
        add(questionLabel, gbc);

        JTextField questionField = new JTextField(30);
        gbc.gridy++;
        add(questionField, gbc);
        questionFields.add(questionField);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridwidth = 2;
    gbc.gridx = 0;
    gbc.gridy = 2;

    questionFields = new ArrayList<>();
    optionFields = new ArrayList<>();
    correctAnswerFields = new ArrayList<>();

    for (int i = 0; i < numOfQuestions; i++) {
        JLabel questionL
        JLabel optionsLabel = new JLabel("Options:");
        gbc.gridy++;
        add(optionsLabel, gbc);

        ArrayList<JTextField> optionFieldsList = new ArrayList<>();
        for (int j = 0; j < 4; j++) {
            JTextField optionField = new JTextField(30);
            gbc.gridy++;
            add(optionField, gbc);
            optionFieldsList.add(optionField);
        }
        optionFields.add(optionFieldsList);

        JLabel correctAnswerLabel = new JLabel("Correct Answer:");
        gbc.gridy++;
        add(correctAnswerLabel, gbc);

        String[] options = {"Option 1", "Option 2", "Option 3", "Option 4"};
        JComboBox<String> correctAnswerComboBox = new JComboBox<>(options);
        gbc.gridy++;
        add(correctAnswerComboBox, gbc);
        correctAnswerFields.add(correctAnswerComboBox);
    }

    gbc.gridy++;
    JLabel timeLimitLabel = new JLabel("Time Limit (in minutes):");
    gbc.gridy++;
    add(timeLimitLabel, gbc);

    timeLimitField = new JTextField(10);
    gbc.gridy++;
    add(timeLimitField, gbc);     
    }
}