import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/Lab_6?serverTimezone=UTC&useSSL=false";
    private static final String user = "root";
    private static final String password = "root";

    private static Connection con;
    private static Statement stmt;


    public static void main(String args[]) {

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();

            showMenu();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
        }

    }

    public static void updateStudent(Connection connection) throws SQLException {
        String sql = "UPDATE student SET first_name=?, last_name=?, name_of_group=?, specialty=? WHERE student_id=?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "Річард");
        statement.setString(2, "Докінз");
        statement.setString(3, "КН-22");
        statement.setString(4, "Інженер");
        statement.setInt(5, 16);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Done");
        }

        statement.close();
    }


    public static void insertStudent(Connection connection) throws SQLException {
        String sql = "INSERT INTO student (first_name, last_name, name_of_group, specialty) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "Володимир");
        statement.setString(2, "Копач");
        statement.setString(3, "КН-21");
        statement.setString(4, "Інженер");

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Done");
        }

        statement.close();
    }

    public static void deleteStudent(Connection connection) throws SQLException {
        String sql = "DELETE FROM student WHERE student_id=?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, 5);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Done");
        }
    }

    public static void selectAllStudents(Statement stmt) throws SQLException {
        String allSelectQuery = "SELECT * FROM Student";
        ResultSet rs = stmt.executeQuery(allSelectQuery);

        while (rs.next()) {
            System.out.println(rs.getInt("student_id") + ": " +
                    rs.getString("first_name") + " " +
                    rs.getString("last_name") + " " +
                    rs.getString("name_of_group") + " " +
                    rs.getString("specialty"));
        }

        try {
            rs.close();
        } catch (SQLException se) {

        }
    }

    public static void showMenu() {
        System.out.println("  Menu:" +
                "\n1. Select data with table" +
                "\n2. Insert data to table" +
                "\n3. Delete data in table" +
                "\n4. Update data in table" +
                "\nAnother number - Exit");

        Scanner in = new Scanner(System.in);

        switch (in.nextInt()) {
            case 1:
                try {
                    selectAllStudents(stmt);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                try {
                    insertStudent(con);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case 3:
                try {
                    deleteStudent(con);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case 4:
                try {
                    updateStudent(con);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            default:
                showMenu();
                break;
        }

        System.out.println("0 - Show menu" +
                "\nAnother number - Exit");

        switch (in.nextInt()) {
            case 0:
                showMenu();
                break;

            default:
                break;
        }
    }

}
