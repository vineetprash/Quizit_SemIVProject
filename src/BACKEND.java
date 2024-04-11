import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;


public class BACKEND {
    private Connection conn;
    // Replace with your database connection details
    String url = "jdbc:oracle:thin:@LAPTOP-EK807I1M:1521:xe";
    String username = "system";
    String password = "SHIVAM";


    public BACKEND() {
        // Initialize database connection
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@LAPTOP-EK807I1M:1521:xe", "system", "SHIVAM");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Function to sign up a new user
    public boolean signUp(String username, String password, String role) {
        try {
            PreparedStatement pstmt = conn
                    .prepareStatement("INSERT INTO users(username, password, role) VALUES (?, ?, ?)");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    // Function to authenticate user login
    public String[] authenticate(String username, String password) {
        return new String[]{"1","teacher"};
        // try {
        //     PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
        //     pstmt.setString(1, username);
        //     pstmt.setString(2, password);
        //     ResultSet rs = pstmt.executeQuery();
        //     if (rs.next()) {
        //         String role = rs.getString("role");
        //         return new String[]{"1", role};
        //     } else {
        //         return new String[]{"0", "NA"};
        //     }
        // } catch (SQLException e) {
        //     e.printStackTrace();
        //     return new String[]{"0", "NA"};
        // }
    }
    
    

    // Function to create a quiz in the database
    public boolean createQuizDB(String quizName, int timeLimit, String scoringCriteria) throws SQLException {
        String sql = "INSERT INTO quizzes(quiz_name, time_limit, scoring_criteria) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, quizName);
            pstmt.setInt(2, timeLimit);
            pstmt.setString(3, scoringCriteria);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }


    // Function to get the ID of a quiz by its name
    public int getQuizId(String quizName) throws SQLException {
        String sql = "SELECT quiz_id FROM quizzes WHERE quiz_name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, quizName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("quiz_id");
            } else {
                throw new SQLException("Quiz not found: " + quizName);
            }
        }
    }

    public String getQuizName(int quizId) throws SQLException {
        String sql = "SELECT quiz_name FROM quizzes WHERE quiz_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quizId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("quiz_name");
            } else {
                throw new SQLException("Quiz not found: " + quizId);
            }
        }
    }

    // Function to add a question to a quiz
    public void addQuestionToQuiz(int quizId, String questionText, String questionType, String options,
            String correctAnswer) throws SQLException {
        String sql = "INSERT INTO questions(quiz_id, question_text, question_type, options, correct_answer) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quizId);
            pstmt.setString(2, questionText);
            pstmt.setString(3, questionType);
            pstmt.setString(4, options);
            pstmt.setString(5, correctAnswer);
            pstmt.executeUpdate();
        }
    }


    
    
    public void storeStudentScore(String username, int score, int quizId) throws SQLException {
        String quizName = getQuizName(quizId);
        try {
            // Prepare SQL statement
            String sql = "INSERT INTO results(student_id, quiz_id, quiz_name, score) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Set parameters
                pstmt.setString(1, username);
                pstmt.setInt(2, quizId);
                pstmt.setString(3, quizName);
                pstmt.setInt(4, score);
                // Execute SQL statement
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Score stored successfully.");
                } else {
                    System.out.println("Failed to store score.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error storing score: " + e.getMessage());
        }
    }
    
    

    // Function to retrieve quiz details (quizId, quizName, timeLimit, scoringCriteria)
    public Object[] getQuizDetails(String quizName) throws SQLException {
        String sql = "SELECT * FROM quizzes WHERE quiz_name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, quizName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int quizId = rs.getInt("quiz_id");
                int timeLimit = rs.getInt("time_limit");
                int scoringCriteria = rs.getInt("scoring_criteria");
                
                // Create and populate an array with the data
                Object[] quizDetails = new Object[4];
                quizDetails[0] = quizId;
                quizDetails[1] = quizName;
                quizDetails[2] = timeLimit;
                quizDetails[3] = scoringCriteria;
                
                return quizDetails;
            }
        }
        return null;
    }
    
    // Function to fetch List of all questions(question_id, question_text, option1, option2, option3, option4, correct_answer)
    public Object[][] fetchQuestionsByQuizId(int quizId) {
        List<Object[]> questionsList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM questions WHERE quiz_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, quizId);
                ResultSet rs = pstmt.executeQuery();
    
                while (rs.next()) {
                    int questionId = rs.getInt("question_id");
                    String questionText = rs.getString("question_text");
                    String option1 = rs.getString("option1");
                    String option2 = rs.getString("option2");
                    String option3 = rs.getString("option3");
                    String option4 = rs.getString("option4");
                    String correctAnswer = rs.getString("correct_answer");
    
                    // Create an Object array to hold question details
                    Object[] questionDetails = {questionId, questionText, option1, option2, option3, option4, correctAnswer};
                    questionsList.add(questionDetails);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Convert the list to a 2D array and return
        return questionsList.toArray(new Object[0][]);
    }
    
    

    public Object[][] viewScores(String username) {
        List<Object[]> scoresList = new ArrayList<>();
        try {
            String sql = "SELECT quiz_id, quiz_name, score FROM results WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String quizId = rs.getString("quiz_id");
                    String quizName = rs.getString("quiz_name");
                    int score = rs.getInt("score");
                    // Create an Object array to hold test ID and score
                    Object[] scoreDetails = {quizId, quizName, score};
                    scoresList.add(scoreDetails);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving scores: " + e.getMessage());
        }
        // Convert the list to a 2D array and return
        return scoresList.toArray(new Object[0][]);
    }


    public List<Object[]> getQuizzes() throws SQLException {
        List<Object[]> quizList = new ArrayList<>();
        String sql = "SELECT DISTINCT quiz_id, quiz_name FROM quizzes";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int quizId = rs.getInt("quiz_id");
                String quizName = rs.getString("quiz_name");
                Object[] quizDetails = {quizId, quizName};
                quizList.add(quizDetails);
            }
        }
        return quizList;
    }
    
}


