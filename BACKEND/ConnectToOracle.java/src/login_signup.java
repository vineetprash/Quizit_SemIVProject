import java.sql.*;
import java.util.Scanner;
import java.lang.String;


public class login_signup {
    private Connection conn;
    // Replace with your database connection details
    String url = "jdbc:oracle:thin:@LAPTOP-EK807I1M:1521:xe";
    String username = "system";
    String password = "SHIVAM";

    public login_signup() {
        // Initialize database connection
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@LAPTOP-EK807I1M:1521:xe", "system", "SHIVAM");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
private static String username1 ;
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
    public LoginResult authenticate(String username, String password) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String role = rs.getString("role");
             
                return new LoginResult(true, role);
            } else {
                return new LoginResult(false, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new LoginResult(false, null);
        }

    }

    // Function to create a quiz
    private void createQuiz(Scanner scanner) {
        try {
            System.out.println("Creating a new quiz...");
            System.out.print("Enter quiz name: ");
            String quizName = scanner.nextLine();
            System.out.print("Enter time limit (in minutes): ");
            int timeLimit = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter scoring criteria (JSON or other structured format): ");
            String scoringCriteria = scanner.nextLine();

            boolean success = createQuizDB(quizName, timeLimit, scoringCriteria);
            if (success) {
                System.out.println("Quiz created successfully!");
                addQuestionsToQuiz(quizName, scanner);
            } else {
                System.out.println("Failed to create quiz.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating quiz: " + e.getMessage());
        }
    }

    // Function to create a quiz in the database
    private boolean createQuizDB(String quizName, int timeLimit, String scoringCriteria) throws SQLException {
        String sql = "INSERT INTO quizzes(quiz_name, time_limit, scoring_criteria) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, quizName);
            pstmt.setInt(2, timeLimit);
            pstmt.setString(3, scoringCriteria);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Function to add questions to a quiz
    private void addQuestionsToQuiz(String quizName, Scanner scanner) {
        try {
            int quizId = getQuizId(quizName);

            System.out.println("Adding Multiple-Choice Questions to Quiz");
            while (true) {
                System.out.print("Enter question text: ");
                String questionText = scanner.nextLine();
                System.out.print("Enter options (in JSON or other structured format): ");
                String options = scanner.nextLine();
                System.out.print("Enter correct answer: ");
                String correctAnswer = scanner.nextLine();

                addQuestionToQuiz(quizId, questionText, "multiple_choice", options, correctAnswer);

                System.out.print("Do you want to add another question? (yes/no): ");
                String choice = scanner.nextLine();
                if (!choice.equalsIgnoreCase("yes")) {
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error adding questions to the quiz: " + e.getMessage());
        }
    }

    // Function to get the ID of a quiz by its name
    private int getQuizId(String quizName) throws SQLException {
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

    // Function to add a question to a quiz
    private void addQuestionToQuiz(int quizId, String questionText, String questionType, String options,
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

    // Function to take a quiz
    private void takeQuiz(Scanner scanner) {
        try {
            System.out.print("Enter quiz name: ");
            String quizName = scanner.nextLine();
    
            // Retrieve quiz details and questions
            Quiz quiz = getQuizDetails(quizName);
    
            // Take the quiz
            if (quiz != null) {
                System.out.println("Quiz Name: " + quiz.getName());
                System.out.println("Time Limit: " + quiz.getTimeLimit() + " minutes");
                System.out.println("Scoring Criteria: " + quiz.getScoringCriteria());
    
                int score = takeQuizDB(quiz);
    
                System.out.println("Your score: " + score);
    
                // Store score in the student table
                if (username1 != null) {
                    storeStudentScore(username1, score, quiz.getName());
                } else {
                    System.out.println("Error: Username not available.");
                }
            } else {
                System.out.println("Quiz not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error taking the quiz: " + e.getMessage());
        }
    }
    
    private void storeStudentScore(String username, int score, String quizId) {
        try {
            // Prepare SQL statement
            String sql = "INSERT INTO student(username, score, quiz_id) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Set parameters
                pstmt.setString(1, username);
                pstmt.setInt(2, score);
                pstmt.setString(3, quizId);
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
    

    // Function to retrieve quiz details
    private Quiz getQuizDetails(String quizName) throws SQLException {
        String sql = "SELECT * FROM quizzes WHERE quiz_name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, quizName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int quizId = rs.getInt("quiz_id");
                int timeLimit = rs.getInt("time_limit");
                String scoringCriteria = rs.getString("scoring_criteria");
                return new Quiz(quizId, quizName, timeLimit, scoringCriteria);
            }
        }
        return null;
    }

    // Function to take the quiz from the database
    private int takeQuizDB(Quiz quiz) {
        try {
            String sql = "SELECT * FROM questions WHERE quiz_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, quiz.getId());
                ResultSet rs = pstmt.executeQuery();
                int score = 0;
                Scanner scanner = new Scanner(System.in);

                long startTime = System.currentTimeMillis();
                long endTime = startTime + (quiz.getTimeLimit() * 60 * 1000); // Convert minutes to milliseconds

                while (rs.next() && System.currentTimeMillis() < endTime) {
                    String questionText = rs.getString("question_text");
                    String options = rs.getString("options");

                    // Display question and options
                    System.out.println(questionText);
                    System.out.println("Options: " + options);

                    // Ask for user's answer
                    System.out.print("Enter your answer: ");
                    String userAnswer = scanner.nextLine();

                    // Compare with correct answer and update score
                    String correctAnswer = rs.getString("correct_answer");
                    if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                        score++;
                    }
                }

                return score;
            }
        } catch (SQLException e) {
            System.out.println("Error taking the quiz: " + e.getMessage());
            return 0;
        }
    }

    private void viewScores(String username) {
        try {
            String sql = "SELECT * FROM student  WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();
                System.out.println("Quiz Name\t\tScore");
                while (rs.next()) {
                    String quizName = rs.getString("quiz_id");
                    int score = rs.getInt("score");
                    System.out.println(quizName + "\t\t" + score);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving scores: " + e.getMessage());
        }
    }


    // Your existing code...



    static class Quiz {
        private int id;
        private String name;
        private int timeLimit;
        private String scoringCriteria;

        public Quiz(int id, String name, int timeLimit, String scoringCriteria) {
            this.id = id;
            this.name = name;
            this.timeLimit = timeLimit;
            this.scoringCriteria = scoringCriteria;
        }

        // Getters
        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getTimeLimit() {
            return timeLimit;
        }

        public String getScoringCriteria() {
            return scoringCriteria;
        }
    }

    class LoginResult {
        private boolean success;
        private String role;
    
        public LoginResult(boolean success, String role) {
            this.success = success;
            this.role = role;
        }
    
        public boolean isSuccess() {
            return success;
        }
    
        public String getRole() {
            return role;
        }
    }
    public static void main(String[] args) {
       
        login_signup quizAppDAO = new login_signup();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to Quiz App!");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.println("Sign Up");
                    System.out.print("Enter username: ");
                    String signUpUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String signUpPassword = scanner.nextLine();
                    System.out.print("Enter role (teacher/student): ");
                    String signUpRole = scanner.nextLine();
                    boolean signUpSuccess = quizAppDAO.signUp(signUpUsername, signUpPassword, signUpRole);
                    if (signUpSuccess) {
                        System.out.println("Sign up successful!");
                    } else {
                        System.out.println("Sign up failed. Username may already exist.");
                    }
                    break;

                case 2:
                    System.out.println("Login");
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                 
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    LoginResult loginResult = quizAppDAO.authenticate(loginUsername, loginPassword);
                    if (loginResult.isSuccess()) {
                        System.out.println("Login successful!");
                        username1 = loginUsername;
                        String role = loginResult.getRole();
                        if (role.equals("teacher")) {
                            // Provide teacher-specific functionalities
                            System.out.println("Teacher functionality: Create Quiz, Add Questions, etc.");

                            while (true) {
                                System.out.println("1. Create Quiz");
                                System.out.println("2. Logout");
                                System.out.print("Enter your choice: ");
                                int teacherChoice = scanner.nextInt();
                                scanner.nextLine(); // Consume newline character

                                switch (teacherChoice) {
                                    case 1:
                                        // Code to create a quiz
                                        quizAppDAO.createQuiz(scanner);
                                        break;
                                    case 2:
                                        System.out.println("Logging out...");
                                        return; // Exit the main loop
                                    default:
                                        System.out.println("Invalid choice. Please enter a valid option.");
                                }
                            }
                        } else if (role.equals("student")) {
                            // Provide student-specific functionalities
                            System.out.println("Student functionality: Take Quiz, View Scores, etc.");
                            while (true) {
                                System.out.println("1. Take Quiz");
                                System.out.println("2. View Scores");
                                System.out.println("3. Logout");
                                System.out.print("Enter your choice: ");
                                int studentChoice = scanner.nextInt();
                                scanner.nextLine(); // Consume newline character

                                switch (studentChoice) {
                                    case 1:
                                        // Code to take a quiz
                                       quizAppDAO.takeQuiz(scanner);
                                        break;
                                    case 2:
                                    quizAppDAO.viewScores(loginUsername);
                                        break;
                                    case 3:
                                        System.out.println("Logging out...");
                                        return; // Exit the main loop
                                    default:
                                        System.out.println("Invalid choice. Please enter a valid option.");
                                }
                            }
                        }
                    } else {
                        System.out.println("Login failed. Invalid username or password.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}

