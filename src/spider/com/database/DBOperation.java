package spider.com.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBOperation {

    public void insertPerson(Connection conn, String name, String lastName, int age){
        try {
            PreparedStatement preper = conn.prepareStatement("INSERT INTO PERSON " +
                    "(Id, Name, LastName, Age) VALUES (?,?,?,?)");
            preper.setInt(1, 1);
            preper.setString(2, name);
            preper.setString(3, lastName);
            preper.setInt(4, age);
            int nuberRecord = preper.executeUpdate();
            System.out.println("Dodano " + nuberRecord + " rekordy");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllPerson(Connection conn){
        try {
            PreparedStatement preper = conn.prepareStatement(
                    "SELECT * FROM PERSON",
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet result = preper.executeQuery();

//            result.first();
//            result.last();
            while(result.next()){
                String name = result.getString("Name");
                String lastName = result.getString(3);
                int age = result.getInt(result.findColumn("Age"));
                System.out.println("rekord z bazy: " + name + " " + lastName + " " +
                        age );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
