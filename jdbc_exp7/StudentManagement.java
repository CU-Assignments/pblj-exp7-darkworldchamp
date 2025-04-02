import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManagement {
  // Database Connection Details
  private static final String URL = "jdbc:mysql://localhost:3306/StudentDB";
  private static final String USER = "root"; // Change if needed
  private static final String PASSWORD = "Nikhil@123"; // Change if needed

  // Model Class - Student
  static class Student {
    private int studentID;
    private String name;
    private String department;
    private float marks;

    public Student(int studentID, String name, String department, float marks) {
      this.studentID = studentID;
      this.name = name;
      this.department = department;
      this.marks = marks;
    }

    public int getStudentID() {
      return studentID;
    }

    public String getName() {
      return name;
    }

    public String getDepartment() {
      return department;
    }

    public float getMarks() {
      return marks;
    }

    @Override
    public String toString() {
      return studentID + " | " + name + " | " + department + " | " + marks;
    }
  }

  // Controller Methods (Database Operations)
  public static void addStudent(Student student) {
    String sql = "INSERT INTO Student (Name, Department, Marks) VALUES (?, ?, ?)";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, student.getName());
      pstmt.setString(2, student.getDepartment());
      pstmt.setFloat(3, student.getMarks());
      pstmt.executeUpdate();
      System.out.println("Student added successfully!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static List<Student> getAllStudents() {
    List<Student> students = new ArrayList<>();
    String sql = "SELECT * FROM Student";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
      while (rs.next()) {
        students.add(new Student(
            rs.getInt("StudentID"),
            rs.getString("Name"),
            rs.getString("Department"),
            rs.getFloat("Marks")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return students;
  }

  public static void updateStudent(Student student) {
    String sql = "UPDATE Student SET Name=?, Department=?, Marks=? WHERE StudentID=?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, student.getName());
      pstmt.setString(2, student.getDepartment());
      pstmt.setFloat(3, student.getMarks());
      pstmt.setInt(4, student.getStudentID());
      pstmt.executeUpdate();
      System.out.println("Student updated successfully!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void deleteStudent(int studentID) {
    String sql = "DELETE FROM Student WHERE StudentID=?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, studentID);
      pstmt.executeUpdate();
      System.out.println("Student deleted successfully!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // View (Menu-driven user interaction)
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("\nStudent Management System");
      System.out.println("1. Add Student");
      System.out.println("2. View Students");
      System.out.println("3. Update Student");
      System.out.println("4. Delete Student");
      System.out.println("5. Exit");
      System.out.print("Choose an option: ");
      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume newline

      switch (choice) {
        case 1:
          System.out.print("Enter Name: ");
          String name = scanner.nextLine();
          System.out.print("Enter Department: ");
          String department = scanner.nextLine();
          System.out.print("Enter Marks: ");
          float marks = scanner.nextFloat();
          addStudent(new Student(0, name, department, marks));
          break;

        case 2:
          List<Student> students = getAllStudents();
          System.out.println("\n📋 Student List:");
          for (Student student : students) {
            System.out.println(student);
          }
          break;

        case 3:
          System.out.print("Enter Student ID to Update: ");
          int id = scanner.nextInt();
          scanner.nextLine();
          System.out.print("Enter New Name: ");
          name = scanner.nextLine();
          System.out.print("Enter New Department: ");
          department = scanner.nextLine();
          System.out.print("Enter New Marks: ");
          marks = scanner.nextFloat();
          updateStudent(new Student(id, name, department, marks));
          break;

        case 4:
          System.out.print("Enter Student ID to Delete: ");
          id = scanner.nextInt();
          deleteStudent(id);
          break;

        case 5:
          System.out.println("Exiting...");
          System.exit(0);

        default:
          System.out.println("Invalid option! Try again.");
      }
    }
  }
}
