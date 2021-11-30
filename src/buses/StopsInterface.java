package buses;

import java.util.ArrayList;
import java.util.Map;

public interface StopsInterface {
    Stop getStopByName(StopName stopName);
    void setStartingStop(StopName startingStop, Time startingTime);
    ArrayList<LineName> getLines(StopName stop);
    Map.Entry<Time, LineName> getReachableAt(StopName stop);
    void resetReachable();
    Map.Entry<StopName, Time> earliestReachableStopAfter(Time time);
    void clean();
}
