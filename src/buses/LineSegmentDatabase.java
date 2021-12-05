package buses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class LineSegmentDatabase extends LineSegment{
    private final String databaseURL;
    private final int segmentId;

    public LineSegmentDatabase(int capacity, LineName lineName, StopName nextStop, TimeDiff timeToNextStop,
                               StopsInterface stops, Map<Time,Integer> initialPassengersCount, String path, int segmentId){
        super(capacity, lineName, nextStop, timeToNextStop, stops);
        databaseURL = path;
        numberOfPassengers = new HashMap<>(initialPassengersCount);
        this.segmentId = segmentId;
    }

    @Override
    public void incrementCapacity(Time startTime){
        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement tmpStatement = null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseURL);
            tmpStatement = connection.prepareStatement("SELECT line_id " +
                                                            "FROM line_segment " +
                                                            "WHERE line_segment_id=?");
            tmpStatement.setInt(1,segmentId);
            ResultSet tmpResultSet = tmpStatement.executeQuery();
            int lineId;
            if(tmpResultSet.next()) lineId = tmpResultSet.getInt("line_id");
            else throw new RuntimeException();
            statement = connection.prepareStatement("UPDATE bus_segment SET passengers_count=number_of_passengers+1 " +
                    "WHERE EXISTS(" +
                    "SELECT * " +
                    "FROM bus b " +
                    "WHERE b.line_id=? AND b.starting_time+(SELECT SUM(ls.time_diff) " +
                                            "FROM line_segment ls " +
                                            "WHERE ls.line_segment_id < ? AND ls.line_id=?" +
                                            ")=?" +
                    ")");
            statement.setInt(1,lineId);
            statement.setInt(2,segmentId);
            statement.setInt(3, lineId);
            statement.setInt(4, startTime.getTime());
            statement.executeUpdate();
            statement.close();
            connection.close();
        }catch (Exception e) {

        }
    }
}
