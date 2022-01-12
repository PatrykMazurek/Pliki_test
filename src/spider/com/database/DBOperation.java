package spider.com.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBOperation {

    public void insertPerson(Connection conn, String name, String lastName, int age){
        try {
            PreparedStatement preper = conn.prepareStatement("INSERT INTO PERSON " +
                    "(Name, LastName, Age) VALUES (?,?,?)");
            preper.setString(1, name);
            preper.setString(2, lastName);
            preper.setInt(3, age);
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
                int id = result.getInt(1);
                String name = result.getString("Name");
                String lastName = result.getString(3);
                int age = result.getInt(result.findColumn("Age"));
                System.out.println("rekord z bazy: " + id + " " + name + " " + lastName + " " +
                        age );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllPersonWithModfi(Connection conn, int id, String name) {
        try {
            PreparedStatement preper = conn.prepareStatement("SELECT * FROM PERSON",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet result = preper.executeQuery();

            while (result.next()) {
                if (result.getInt("Id") == id) {
                    System.out.println("rekord numer " + result.getInt("Id") + " usunięty ");
//                    result.updateString("City", newCity);
//                    result.updateRow();
                    result.deleteRow();
                } else {
                    System.out.println("id) " + result.getInt(1) + " imie " + result.getString("Name") +
                            " Nazwisko " + result.getString(result.findColumn("LastName")) +
                            " Wiek " + result.getInt("City"));
                }
            }
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
