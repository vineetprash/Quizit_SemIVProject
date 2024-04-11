import java.sql.*;

public class App {

    public static void main(String[] args) {
        // Replace with your database connection details
        String url = "jdbc:oracle:thin:@LAPTOP-EK807I1M:1521:xe";
        String username = "system";
        String password = "SHIVAM";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Load the JDBC driver
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            // SQL query to select all columns from the shivam1 table
            String query = "SELECT * FROM shivam1";
            ResultSet resultSet = statement.executeQuery(query);

            // Print the result of the query
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                  String value = resultSet.getString(i);
                  System.out.println("Column " + i + ": " + value);
                }
                System.out.println(); // Print a newline after each row
              }
              
              

            resultSet.close();
            statement.close();
            connection.close();

            System.out.println("Connection closed successfully.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
