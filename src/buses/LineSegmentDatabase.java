package buses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class LineSegmentDatabase extends LineSegment{
    private final String databaseURL;

    public LineSegmentDatabase(int capacity, LineName lineName, StopName nextStop, TimeDiff timeToNextStop, StopsInterface stops, String databaseURL){
        super(capacity, lineName, nextStop, timeToNextStop, stops);
        this.databaseURL = databaseURL;
    }

    @Override
    public void incrementCapacity(Time startTime){
        Connection connection = null;
        String statement = "UPDATE line_segment SET number_of_passengers=number_of_passengers+2" +
                "WHERE stop_name=" + nextStop.getStopName() + "AND time=" + Integer.toString(startTime.getTime());
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseURL);
            PreparedStatement pts = connection.prepareStatement(statement);
            pts.executeUpdate();
        }catch (Exception e){

        }
    }
}
