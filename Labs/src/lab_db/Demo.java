package lab_db;

import java.sql.*;
import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/nation", "root", "123");
            System.out.println("Connection successed");

            PreparedStatement stmt = null;

            Scanner scan = new Scanner(System.in);
            System.out.print("Which country do you want? ");
            String s = scan.nextLine();

            String sql = "select * from countries WHERE `name` LIKE ?;";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, s);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.printf("%5d\t%s\t%.2f\n", rs.getInt("country_id"), rs.getString(2), rs.getFloat("area"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}