package userAuth;

import java.util.HashMap;
import java.util.Map;

public class UserAuthenticator {

    // Take data from JDBC
    /*
        try {
            // Establish connection to Oracle database
            String url = "jdbc:oracle:thin:@localhost:1521:ORCL"; // Change the URL accordingly
            connection = DriverManager.getConnection(url, "your_database_username", "your_database_password");

            // Create a PreparedStatement to execute SQL query
            String query = "SELECT password FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            // Execute the query and fetch the result
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Retrieve the password from the result set
                String dbPassword = resultSet.getString("password");
                // Check if the passwords match
                return password.equals(dbPassword);
            }
        } catch (SQLException ex) {
            // Handle any SQL exceptions
            JOptionPane.showMessageDialog(this, "Failed to authenticate user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            // Close the connection when done
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
     */
    static Map<String, String> userPasswordMap = new HashMap<>() {
        {
            put("vineet", "password");
            put("sumit", "password");
            put("shivam", "password");

        }
    };

    public static boolean authenticateLogin(String username, String password) {
        String value = userPasswordMap.get(username);

        return true; ///
        // if (value != null && value.equals(password)) {
        //     return true;
        // }
        // else {
        //     return false;
        // }
    }
}
