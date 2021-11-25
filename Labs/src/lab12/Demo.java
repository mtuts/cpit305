package lab12;

import java.io.IOException;
import java.sql.*;

public class Demo {
  final static String DB_URL = "jdbc:mysql://localhost/nation";
  final static String DB_USER = "root";
  final static String DB_PASS = "123";

  public static void main(String[] args) {
    Connection conn = null;
    Statement  stmt = null;
    PreparedStatement pstm = null;
    try {
      conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
      stmt = conn.createStatement();
      pstm = conn.prepareStatement("select * from countries WHERE `name` LIKE ? LIMIT 10;");
      pstm.setString(1, "%Saudi%");
      //ResultSet rs = stmt.executeQuery("select * from countries LIMIT 10;");
      ResultSet rs = pstm.executeQuery();
      while (rs.next()) {
        System.out.printf("%d\t%s\n", rs.getInt(1), rs.getString(2));
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
  }
}
