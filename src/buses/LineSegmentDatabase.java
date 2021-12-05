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
        PreparedStatement getLineId = null;
        PreparedStatement getTotalTimeDiff = null;
        PreparedStatement getBusId = null;
        PreparedStatement updateCapacity = null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseURL);
            getLineId = connection.prepareStatement("SELECT line_id " +
                                                            "FROM line_segment " +
                                                            "WHERE line_segment_id=?");
            getLineId.setInt(1,segmentId);
            ResultSet tmpResultSet = getLineId.executeQuery();
            int lineId;
            if(tmpResultSet.next()) lineId = tmpResultSet.getInt("line_id");
            else throw new RuntimeException();

            getTotalTimeDiff = connection.prepareStatement("SELECT SUM(ls.time_diff) AS result FROM line_segment ls WHERE ls.line_segment_id < ? AND ls.line_id = ?");
            getTotalTimeDiff.setInt(1,segmentId);
            getTotalTimeDiff.setInt(2, lineId);
            ResultSet totalTimeDiffResultSet = getTotalTimeDiff.executeQuery();
            int totalTimeDiff;
            if(totalTimeDiffResultSet.next()) totalTimeDiff = totalTimeDiffResultSet.getInt("result");
            else throw new RuntimeException();

            getBusId = connection.prepareStatement("SELECT bus_id FROM bus WHERE starting_time + ? = ? AND line_id = ?");
            getBusId.setInt(1, totalTimeDiff);
            getBusId.setInt(2, startTime.getTime());
            getBusId.setInt(3, lineId);
            int busId;
            ResultSet busResultSet = getBusId.executeQuery();
            if(busResultSet.next()) busId = busResultSet.getInt("bus_id");
            else throw new RuntimeException();

            updateCapacity = connection.prepareStatement("UPDATE bus_segment SET passengers_count=passengers_count+1 " +
                    "WHERE line_segment_id = ? AND bus_id = ?");
            updateCapacity.setInt(1, segmentId);
            updateCapacity.setInt(2, busId);
            updateCapacity.executeUpdate();

            updateCapacity.close();
            getTotalTimeDiff.close();
            getLineId.close();
            getBusId.close();
            connection.close();
        }catch (Exception e) {

        }
    }
}
