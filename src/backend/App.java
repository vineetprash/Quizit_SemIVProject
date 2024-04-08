package backend;
import java.sql.*;

public class App {
    public String url = "jdbc:oracle:thin:@LAPTOP-EK807I1M:1521:xe";
    public String username = "system";
    public String password = "SHIVAM";
    public App() {
        System.out.println(password);
    }
    // public static void App(String[] args) {
    //     new App();
    // }
    // public static void App(String[] args) {
    //     String url = App.url;
    //     String password = App.password;
    //     String username = App.username;

    //     try {
    //         Class.forName("oracle.jdbc.driver.OracleDriver"); 
    //         Connection connection = DriverManager.getConnection(url, username, password);

    //         Statement statement = connection.createStatement();

    //         String query = "SELECT * FROM shivam1";
    //         ResultSet resultSet = statement.executeQuery(query);

   
    //         while (resultSet.next()) {
    //             for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
    //               String value = resultSet.getString(i);
    //               System.out.println("Column " + i + ": " + value);
    //             }
    //             System.out.println(); 
    //           }
              
              

    //         resultSet.close();
    //         statement.close();
    //         connection.close();

    //         System.out.println("Connection closed successfully.");
    //     } catch (ClassNotFoundException e) {
    //         e.printStackTrace();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }
}

