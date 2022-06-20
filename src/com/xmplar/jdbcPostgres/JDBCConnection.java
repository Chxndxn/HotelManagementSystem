package com.xmplar.jdbcPostgres;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnection {

  static Connection con = null;

  public static void Connection() {
    String host = "localhost";
    String port = "5432";
    String db_name = "HotelManagement";
    String userName = "postgres";
    String password = "password";

    try {
      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection(
          "jdbc:postgresql://" + host + ":" + port + "/" + db_name + "",
          userName,
          password);
      if (con != null) {
        System.out.println("Connected to " + host + ":" + port + "/" + db_name + "");
      } else {
        System.out.println("Connection Failed");
      }
    } catch (Exception e) {
    }
  }
}
