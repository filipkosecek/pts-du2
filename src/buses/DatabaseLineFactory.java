package buses;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseLineFactory implements AbstractLineFactory{
    private final String databaseURL;
    private final StopsInterface stopsInterface;

    public DatabaseLineFactory(String path, StopsInterface stopsInterface){
        this.databaseURL = "jdbc:sqlite:" + path;
        this.stopsInterface = stopsInterface;
    }

    @Override
    public Line createLine(LineName lineName){
        Connection connection = null;
        PreparedStatement getStartingTimesStatement = null;
        PreparedStatement getLineSegments = null;
        PreparedStatement getInitialPassengersCount = null;
        ArrayList<LineSegment> lineSegmentDatabases = new ArrayList<>();
        ArrayList<Time> startingTimes = new ArrayList<>();
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseURL);
            getStartingTimesStatement = connection.prepareStatement("SELECT * " +
                    "FROM bus b,line l " +
                    "WHERE b.line_id=l.line_id AND l.line_name=? " +
                    "ORDER BY b.starting_time ASC");
            getStartingTimesStatement.setString(1,lineName.getLineName());
            ResultSet rs = getStartingTimesStatement.executeQuery();
            StopName firstStop = new StopName(rs.getString("first_stop"));
            while(rs.next()){
                startingTimes.add(new Time(rs.getInt("starting_time")));
            }

            getLineSegments = connection.prepareStatement("SELECT * " +
                    "FROM line_segment ls,line l " +
                    "WHERE ls.line_id=l.line_id AND l.line_name=? " +
                    "ORDER BY ls.line_segment_id");
            getLineSegments.setString(1,lineName.getLineName());
            rs = getLineSegments.executeQuery();
            ArrayList<Time> tmpStartingTimes = new ArrayList<>(startingTimes);
            getInitialPassengersCount = connection.prepareStatement("SELECT * FROM bus_segment " +
                    "WHERE line_segment_id=?");
            while(rs.next()){
                HashMap<Time,Integer> initialNumberOfPassengers = new HashMap<>();
                getInitialPassengersCount.setInt(1, rs.getInt("line_segment_id"));
                ResultSet passengersCountSet = getInitialPassengersCount.executeQuery();
                for(int i = 0; i < tmpStartingTimes.size() && passengersCountSet.next(); i++){
                    if(!passengersCountSet.next()) throw new RuntimeException();
                    initialNumberOfPassengers.put(tmpStartingTimes.get(i), passengersCountSet.getInt("passengers_count"));
                    tmpStartingTimes.set(i, new Time(tmpStartingTimes.get(i).getTime() + rs.getInt("time_diff")));
                }
                lineSegmentDatabases.add(new LineSegmentDatabase(rs.getInt("capacity"),lineName,
                        new StopName(rs.getString("next_stop")),new TimeDiff(rs.getInt("time_diff")),
                        stopsInterface, initialNumberOfPassengers,databaseURL,rs.getInt("line_segment_id")));
            }
            getStartingTimesStatement.close();
            getLineSegments.close();
            getInitialPassengersCount.close();
            connection.close();
            return new Line(lineName,firstStop,startingTimes,lineSegmentDatabases);
        }catch(Exception e){
            return null;
        }
    }
}
