package spider.com.database;


import java.sql.*;
import java.util.Properties;

// Class.forName(org.postgresql.Drivers);
// System.setProperty(jdbc.Driver, com.mysql.jdbc.Drivers)

public class DBConnection {

    public DBConnection(){

    }

    public Connection connectToSQLite(){
        String urlConnection = "jdbc:sqlite:baza.db";
        try {
            Connection conn = DriverManager.getConnection(urlConnection);
            if (conn != null){
                System.out.println("Nawiązanie połączenia");
                return conn;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return null;
    }

    public Connection connectToMySql(){
        String url = "jdbc:mysql://localhost:port";
        System.setProperty("jdbc.Driver", "com.mysql.jdbc.Drivers");
        Properties properties = new Properties();
        properties.setProperty("user", "user");
        properties.setProperty("pass", "pass");
        try {
            Connection conn = DriverManager.getConnection(url, properties);
            if (conn != null){
                System.out.println("Nawiązanie połączenia");
                return conn;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return null;
    }

    public void stopConnection(Connection conn){
        try {
            if (!conn.isClosed()){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void CreateTable( Connection conn){
        try {
            PreparedStatement statement = conn.prepareStatement("CREATE TABLE IF NOT EXISTS PERSON " +
                    "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Name TEXT," +
                    "LastName TEXT," +
                    "Age INTEGER) ");
            if(statement.execute()){
                System.out.println("Utworzono bazę danych ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Savepoint setPoint(Connection con){
        try {
            Savepoint point = con.setSavepoint();
            con.setAutoCommit(false);
            System.out.println("Wykonano punkt przywracania");
            return point;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void getPoint(Connection conn, Savepoint point){
        try {
            conn.rollback(point);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
