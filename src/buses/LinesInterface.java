package buses;

import java.util.ArrayList;

public interface LinesInterface {
    void updateReachable(ArrayList<LineName> lines, Time time, StopName stopName);
    StopName updateCapacityAndGetPreviousStop(LineName lineName, StopName stop, Time time);
    void clean();
}
