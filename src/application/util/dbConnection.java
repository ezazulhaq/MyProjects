package application.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection
{
  private static final String URL = "jdbc:sqlite::resource:note_book.sqlite";
  
  public static Connection getConnection()
    throws SQLException
  {
    try
    {
      Class.forName("org.sqlite.JDBC");
      
      return DriverManager.getConnection(URL);
    }
    catch (ClassNotFoundException|SQLException ex)
    {
      ex.printStackTrace();
    }
    return null;
  }
}
