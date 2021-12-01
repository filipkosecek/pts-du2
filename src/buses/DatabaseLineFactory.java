package buses;

import java.sql.*;

public class DatabaseLineFactory implements AbstractLineFactory{
    private final String databaseURL;

    public DatabaseLineFactory(String databaseURL){
        this.databaseURL = databaseURL;
    }

    @Override
    public Line createLine(LineName lineName){
        Statement statement = null;
        Connection connection = null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseURL);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM eeea");
        }catch(Exception e){

        }

        return null;
    }
}
