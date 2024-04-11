
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class App1 {
    public String url = "jdbc:oracle:thin:@LAPTOP-EK807I1M:1521:xe";
    public String username = "system";
    public String password = "SHIVAM";
    public App1() {
        System.out.println("HELLO");
    }
    public static void main(String[] args) {
        App1 app = new App1();
        String sqlFile = "database.sql"; // Path to your SQL schema file
        String url = app.url;
        String username = app.username;
        String password = app.password;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            // Read SQL script from file
            StringBuilder sqlCommands = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(sqlFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sqlCommands.append(line).append("\n");
                }
            }

            // Execute SQL commands
            String[] commands = sqlCommands.toString().split(";");
            for (String command : commands) {
                statement.addBatch(command);
            }
            statement.executeBatch();

            System.out.println("Schema created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


