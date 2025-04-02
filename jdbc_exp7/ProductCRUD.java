import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
  private static final String URL = "jdbc:mysql://localhost:3306/ProductDB";
  private static final String USER = "root";
  private static final String PASSWORD = "Nikhil@123";

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        System.out.println("\nProduct Management System");
        System.out.println("1. Add Product");
        System.out.println("2. View Products");
        System.out.println("3. Update Product");
        System.out.println("4. Delete Product");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
          case 1:
            addProduct(scanner);
            break;
          case 2:
            viewProducts();
            break;
          case 3:
            updateProduct(scanner);
            break;
          case 4:
            deleteProduct(scanner);
            break;
          case 5:
            System.exit(0);
          default:
            System.out.println("Invalid option! Try again.");
        }
      }
    }
  }

  private static void addProduct(Scanner scanner) {
    System.out.print("Enter product name: ");
    String name = scanner.nextLine();
    System.out.print("Enter price: ");
    double price = scanner.nextDouble();
    System.out.print("Enter quantity: ");
    int quantity = scanner.nextInt();

    String sql = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      conn.setAutoCommit(false);
      pstmt.setString(1, name);
      pstmt.setDouble(2, price);
      pstmt.setInt(3, quantity);
      pstmt.executeUpdate();
      conn.commit();
      System.out.println("Product added successfully.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static void viewProducts() {
    String sql = "SELECT * FROM Product";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
      System.out.println("\nProduct List:");
      while (rs.next()) {
        System.out.println(rs.getInt("ProductID") + " | " + rs.getString("ProductName") +
            " | " + rs.getDouble("Price") + " | " + rs.getInt("Quantity"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static void updateProduct(Scanner scanner) {
    System.out.print("Enter product ID to update: ");
    int id = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Enter new name: ");
    String name = scanner.nextLine();
    System.out.print("Enter new price: ");
    double price = scanner.nextDouble();
    System.out.print("Enter new quantity: ");
    int quantity = scanner.nextInt();

    String sql = "UPDATE Product SET ProductName=?, Price=?, Quantity=? WHERE ProductID=?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      conn.setAutoCommit(false);
      pstmt.setString(1, name);
      pstmt.setDouble(2, price);
      pstmt.setInt(3, quantity);
      pstmt.setInt(4, id);
      pstmt.executeUpdate();
      conn.commit();
      System.out.println("Product updated successfully.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static void deleteProduct(Scanner scanner) {
    System.out.print("Enter product ID to delete: ");
    int id = scanner.nextInt();

    String sql = "DELETE FROM Product WHERE ProductID=?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      conn.setAutoCommit(false);
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      conn.commit();
      System.out.println("Product deleted successfully.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
