import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class NewQuizPanel extends JPanel {
    
    private JTextField numOfQuestionsField;
    private JTextField quizNameField;
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


        // Quiz name field
        JLabel quizNameLabel = new JLabel("Quiz name:");
        quizNameLabel.setForeground(lightColor);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(quizNameLabel, gbc);
        quizNameField = new JTextField(10);
        quizNameField.setBackground(darkColor);
        quizNameField.setForeground(lightColor);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(quizNameField, gbc);

        // Number of questions field
        JLabel numOfQuestionsLabel = new JLabel("Number of Questions:");
        numOfQuestionsLabel.setForeground(lightColor);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(numOfQuestionsLabel, gbc);
        numOfQuestionsField = new JTextField(10);
        numOfQuestionsField.setBackground(darkColor);
        numOfQuestionsField.setForeground(lightColor);
        gbc.gridx = 1;
        gbc.gridy = 1;
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
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backButton, gbc);


	    localApp.showPanel(this);
    }

    private void createQuiz() {
        String quizName = quizNameField.getText();
        int numOfQuestions = Integer.parseInt(numOfQuestionsField.getText());
        int timeLimit = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Time Limit (in minutes):"));
        String scoringCriteria = JOptionPane.showInputDialog(this, "Enter Scoring Criteria:");
        try {
            // Create the quiz in the database
            boolean quizCreated = localBackend.createQuizDB(quizName, timeLimit, scoringCriteria);
            if (!quizCreated) {
                JOptionPane.showMessageDialog(this, "Failed to create quiz", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Get the ID of the newly created quiz
            int quizId = localBackend.getQuizId(quizName);
    
            // Render the question fields and obtain the frame
            JFrame tempFrame = renderQuestionFields(numOfQuestions, "Enter question details: ", quizId);
            tempFrame.setVisible(true);
            tempFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
            // Retrieve all fields from the frame
            // ArrayList<ArrayList<Component>> allFieldsList = (ArrayList<ArrayList<Component>>) tempFrame.getContentPane().getClientProperty("allFieldsList");
    
            // Add questions to the quiz in the database
            // handleSubmit(allFieldsList, quizId);
    
            
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle the exception properly in your application
            JOptionPane.showMessageDialog(this, "Failed to create quiz", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private JFrame renderQuestionFields(int numOfQuestions, String title, int quizId) {
        JPanel questionFieldsPanel = new JPanel(new GridBagLayout());
        questionFieldsPanel.setBackground(darkColor);
        questionFieldsPanel.setForeground(lightColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
    
        // List to store all text fields and option fields for each question
        ArrayList<ArrayList<Component>> allFieldsList = new ArrayList<>();
    
        for (int i = 0; i < numOfQuestions; i++) {
            JLabel questionLabel = new JLabel("Question " + (i + 1) + ":");
            questionLabel.setForeground(lightColor);
            questionFieldsPanel.add(questionLabel, gbc);
    
            gbc.gridy++;
            JTextField questionField = new JTextField(30);
            questionField.setForeground(lightColor);
            questionField.setBackground(darkColor);
            questionFieldsPanel.add(questionField, gbc);
    
            // List to store fields for each question
            ArrayList<Component> questionFieldsList = new ArrayList<>();
            // Add question field to the list of fields for this question
            questionFieldsList.add(questionField);
    
            gbc.gridy++;
            JLabel optionsLabel = new JLabel("Options:");
            optionsLabel.setForeground(lightColor);
            optionsLabel.setBackground(darkColor);
            questionFieldsPanel.add(optionsLabel, gbc);
    
            for (int j = 0; j < 4; j++) {
                gbc.gridy++;
                JTextField optionField = new JTextField(30);
                optionField.setBackground(darkColor);
                optionField.setForeground(lightColor);
                questionFieldsPanel.add(optionField, gbc);
                // Add option field to the list of fields for this question
                questionFieldsList.add(optionField);
            }

            gbc.gridy++;
            JLabel correctAnswerLabel = new JLabel("Correct Answer:");
            questionFieldsPanel.add(correctAnswerLabel, gbc);
    
            gbc.gridy++;
            String[] options = {"Option 1", "Option 2", "Option 3", "Option 4"};
            JComboBox<String> correctAnswerComboBox = new JComboBox<>(options);
            questionFieldsPanel.add(correctAnswerComboBox, gbc);
            // Add correct answer combo box to the list of fields for this question
            questionFieldsList.add(correctAnswerComboBox);
    
            // Add the list of fields for this question to the list of all fields
            allFieldsList.add(questionFieldsList);
    
            gbc.gridy++;
            gbc.insets = new Insets(20, 0, 20, 0); // Add some space between questions
        }
    
        gbc.gridy++;
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton submitButton = new JButton("Submit");
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        questionFieldsPanel.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            handleSubmit(allFieldsList, quizId);
        });
        
    
        JScrollPane scrollPane = new JScrollPane(questionFieldsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
        JFrame frame = new JFrame(title);
        frame.add(scrollPane);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
    
        // Store the list of all fields in the frame's client properties
        ((JComponent) frame.getContentPane()).putClientProperty("allFieldsList", allFieldsList);
    
        return frame;
    }


    private void handleSubmit(ArrayList<ArrayList<Component>> allFieldsList, int quizId) {
        try {
            for (ArrayList<Component> questionFields : allFieldsList) {
                // Extract data from the list of fields for each question
                JTextField questionField = (JTextField) questionFields.get(0);
                String questionText = questionField.getText();
    
                // Extract options
                ArrayList<String> options = new ArrayList<>();
                for (int i = 1; i <= 4; i++) {
                    JTextField optionField = (JTextField) questionFields.get(i);
                    options.add(optionField.getText());
                }
    
                // Extract correct answer
                JComboBox<String> correctAnswerComboBox = (JComboBox<String>) questionFields.get(5);
                String correctAnswer = (String) correctAnswerComboBox.getSelectedItem();
    
                // Add the question to the quiz in the database
                localBackend.addQuestionToQuiz(quizId, questionText, "Type", options.get(0), options.get(1), options.get(2), options.get(3), correctAnswer);
            }
            JOptionPane.showMessageDialog(this, "Questions added to quiz successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle the exception properly in your application
            JOptionPane.showMessageDialog(this, "Failed to add questions to quiz", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Quiz created successfully", "Success", JOptionPane.OK_OPTION);
        new LandingPage(localApp, localBackend);

    }
    
    
    



}
