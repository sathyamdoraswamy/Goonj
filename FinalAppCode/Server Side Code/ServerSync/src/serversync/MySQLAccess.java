/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serversync;

/**
 *
 * @author mridu
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import com.mysql.jdbc.*;


//all we need here is to be able to insert the new row to the corresponding table

public class MySQLAccess {
  private Connection connect = null;
  private PreparedStatement preparedStatement = null;


    public void insertValue(String stmt)
    {
    try {
      // This will load the MySQL driver, each DB has its own driver
      Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      connect = DriverManager
          .getConnection("jdbc:mysql://localhost/goonjModDB?"
              + "user=root&password=goonjWebsite");


      preparedStatement = connect.prepareStatement(stmt);
      preparedStatement.executeUpdate();

    } catch (Exception e) {
        System.out.println("EXCEPTION THROWN MYSQLACCESS!!!");
        e.printStackTrace();
    } finally {
      close();
    }
    }

      // You need to close the resultSet
  private void close() {
    try {
      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }
}
