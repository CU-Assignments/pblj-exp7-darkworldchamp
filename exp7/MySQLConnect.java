import java.sql.*;

public class MySQLConnect {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/your_database";
        String user = "root"; // Change if using a different user
        String password = "Nikhil@123"; // Use your MySQL root password

        String query = "SELECT EmpID, Name, Salary FROM Employee";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Connected to MySQL!");
            System.out.println("EmpID | Name | Salary");
            System.out.println("----------------------");

            while (rs.next()) {
                int id = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");

                System.out.println(id + " | " + name + " | " + salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
