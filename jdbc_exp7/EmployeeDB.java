import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeDB {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/CompanyDB"; 
        String user = "root";
        String password = "Nikhil@123"; 

        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
                System.out.println("SQL connected");
            }

            Statement stmt = conn.createStatement();

            String query = "SELECT * FROM Employee";
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("EmpID\tName\t\tSalary");
            while (rs.next()) {
                int empID = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                System.out.println(empID + "\t" + name + "\t\t" + salary);
            }

            // Close resources
            rs.close();
            stmt.close();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        } finally {
            // Close connection if it was established
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
