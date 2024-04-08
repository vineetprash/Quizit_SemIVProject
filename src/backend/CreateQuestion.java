package backend;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateQuestion {

    private App app = new App();
    


    public CreateQuestion(String title, ArrayList<String> options, int correctOption) {
        String url = app.url;
        String password = app.password;
        String username = app.username;

        // SQL query to insert a new question
        String insertQuery = "INSERT INTO Question (title, answer, creator_id) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            // Convert options array to a single string separated by a delimiter
            String answer = String.join(",", options);

            // Set parameters for the PreparedStatement
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, answer);
            preparedStatement.setInt(3, 1); // Assuming creator_id is 1 for the sake of example

            // Execute the query
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                // Retrieve the auto-generated question_id
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int questionId = generatedKeys.getInt(1);
                    System.out.println("Question inserted with question_id: " + questionId);
                } else {
                    System.out.println("Failed to retrieve question_id.");
                }
            } else {
                System.out.println("Insertion failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void App(String[] args) {
        ArrayList<String> a = new ArrayList<String>(){{add("a"); add("b"); add("c"); add("d");}};

        new CreateQuestion("Question 1", a, 2);
    }

}
