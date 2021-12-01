package buses;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseStopFactory implements AbstractStopFactory{
    private final String databaseURL;

    public DatabaseStopFactory(String databaseURL){
        this.databaseURL = databaseURL;
    }

    @Override
    public Stop createStop(StopName stopName){
        Statement statement = null;
        Connection connection = null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseURL);
            statement = connection.createStatement();
            ArrayList<LineName> lineNames = new ArrayList<>();
            ResultSet rs = statement.executeQuery("SELECT l.line_name" +
                    "FROM stop_line sl,line l,stop s" +
                    "WHERE s.stop_name=" + stopName.getStopName() + " AND sl.stop_id=s.stop_id AND sl.line_id=l.line_id");
            while(rs.next()){
                lineNames.add(new LineName(rs.getString("stop_name")));
            }
            return new Stop(stopName, lineNames);
        }catch(Exception e){
            return null;
        }
    }
}
