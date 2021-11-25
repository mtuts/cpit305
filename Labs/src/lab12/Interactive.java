package lab12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;


public class Interactive {
  final static String MENU_LIST_ALL_TABLE = "1";
  final static String MENU_DESCRIBE_TABLE = "2";
  final static String MENU_COUNTRY_REGION = "3";
  final static String MENU_INSERT_CAR_INF = "4";
  final static String MENU_EXIT = "9";
  final static String DB_URL = "jdbc:mysql://localhost/nation";
  final static String DB_USER = "root";
  final static String DB_PASS = "123";

  
  static Connection conn = null;
  static BufferedReader reader = null;
  static ArrayList<String> tables;
  
  public static void main(String[] args) {
    boolean flag = true;
    tables = new ArrayList<>();
    try {
      conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
      reader = new BufferedReader(new InputStreamReader(System.in));
      while (flag) {
        printMenu();
        String line = reader.readLine();

        switch (line) {
        case MENU_LIST_ALL_TABLE:
            listAllTables();
            break;
        case MENU_DESCRIBE_TABLE:
            describeTable();
            break;
        case MENU_COUNTRY_REGION:
            countriesRegion();
            break;

        case MENU_INSERT_CAR_INF:
          insertNewCar();
          break;
        case MENU_EXIT:
            flag = false;
            continue;
        }

  /*
          pstm = conn.prepareStatement("select * from countries WHERE `name` LIKE ? LIMIT 10;");
          pstm.setString(1, "%Saudi%");
          //ResultSet rs = stmt.executeQuery("select * from countries LIMIT 10;");
          ResultSet rs = pstm.executeQuery();
          while (rs.next()) {
          System.out.printf("%d\t%s\n", rs.getInt(1), rs.getString(2));
          }
          */
      }
    } catch (SQLException | IOException e) {
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

  private static void printMenu() {
      System.out.println("\n-------------------------------------");
      System.out.println("Welcome to Nation Database");
      System.out.println("-------------------------------------\n");
      System.out.println(MENU_LIST_ALL_TABLE + ". List All tables");
      System.out.println(MENU_DESCRIBE_TABLE + ". Describe table");
      System.out.println(MENU_COUNTRY_REGION + ". List Countries with Region");
      System.out.println(MENU_INSERT_CAR_INF + ". Insert new car");
      System.out.println(MENU_EXIT + ". Exit");

      System.out.print("Your choice: ");

  }

  private static void listAllTables() throws SQLException {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SHOW TABLES;");
      int i = 1;
      System.out.println("\n Nation DB Tables:\n");
      tables.clear();
      while (rs.next()) {
          System.out.printf("%3d. %s\n", i, rs.getString(1));
          tables.add(rs.getString(1));
          i++;
      }
      System.out.println();
  }

  private static void describeTable() throws SQLException, IOException {
      listAllTables();
      System.out.print("Enter table number: ");
      int num = Integer.parseInt(reader.readLine());

      System.out.printf("\n----- %s ------\n", tables.get(num - 1));
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("DESCRIBE " + tables.get(num - 1) + ";");
      while (rs.next()) {
          for (int i = 0; i < 6; i++) {
              System.out.printf("%s\t", rs.getString(i + 1));
          }
          System.out.println();
      }
  }

  private static void countriesRegion() throws SQLException, IOException {
    System.out.print("Where do you want record to start: ");
    int b = Integer.parseInt(reader.readLine());
    System.out.print("Enter number of records to display: ");
    int n = Integer.parseInt(reader.readLine());

    String sql = "SELECT c.name AS country, r.name AS region FROM `countries` c ";
    sql += "RIGHT OUTER JOIN regions r ON (c.region_id = r.region_id) ";
    sql += " LIMIT ?, ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, b);
    stmt.setInt(2, n);
    ResultSet rs = stmt.executeQuery();
    System.out.println("---------------------------------------------------------------");
    System.out.printf("%-30s | %-30s\n", "Country", "Region");
    System.out.println("-------------------------------|-------------------------------");
    while (rs.next()) {
      System.out.printf("%-30s | %-30s\n", rs.getString(1), rs.getString(2));
    }
    System.out.println("---------------------------------------------------------------");
  }

  private static void insertNewCar() throws SQLException, IOException {
    System.out.print("Enter Car Model: ");
    String model = reader.readLine();
    
    System.out.print("Enter Car Year: ");
    int year = Integer.parseInt(reader.readLine());
    
    String sql = "INSERT INTO car (model, year) VALUES (?, ?);";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, model);
    ps.setInt(2, year);

    int n = ps.executeUpdate();

    System.out.println(n + " recored(s) have been added");

  }
}
