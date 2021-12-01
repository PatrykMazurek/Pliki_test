package spider.com.database;


import java.sql.*;

public class DBConnection {

    public DBConnection(){

        String urlConnection = "jdbc:sqlite:baza.db";

        try {
//            Class.forName("org.sqlite");

            Connection conn = DriverManager.getConnection(urlConnection);
            if (conn != null){
                Statement statement = conn.createStatement();
                statement.execute("create table if not exists temp " +
                        "(id Integer," +
                        "name Text");
                conn.close();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
