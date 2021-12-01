package buses;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseStopFactory implements AbstractStopFactory{
    private final String databaseURL;

    public DatabaseStopFactory(String path){
        databaseURL = "jdbc:sqlite:" + path;
    }

    @Override
    public Stop createStop(StopName stopName){
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseURL);
            statement = connection.prepareStatement("SELECT l.line_name " +
                    "FROM stop_line sl,line l,stop s " +
                    "WHERE s.stop_name=? AND sl.stop_id=s.stop_is AND sl.line_id=l.line_id");
            statement.setString(1,stopName.getStopName());
            ArrayList<LineName> lineNames = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                lineNames.add(new LineName(rs.getString("line_name")));
            }
            return new Stop(stopName, lineNames);
        }catch(Exception e){
            return null;
        }finally {
            try{
                statement.close();
                connection.close();
            }catch(Exception e){

            }
        }
    }
}
