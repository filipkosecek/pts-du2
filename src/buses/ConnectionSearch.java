package buses;

import java.util.List;
import java.util.Map;
import java.util.LinkedList;

public class ConnectionSearch {
    private final StopsInterface stops;
    private final LinesInterface lines;

    public ConnectionSearch(StopsInterface stops, LinesInterface lines){
        this.stops = stops;
        this.lines = lines;
    }

    public List<Triplet<StopName,Time,LineName>> search(StopName from, StopName to, Time when){
        LinkedList<Triplet<StopName,Time,LineName>> result = new LinkedList<>();
        stops.resetReachable();
        stops.setStartingStop(from, when);
        StopName currentStop = from;
        Time currentTime = new Time(when.getTime());
        while(!currentStop.equals(to)){
            lines.updateReachable(stops.getLines(currentStop),currentTime, currentStop);
            Map.Entry<StopName,Time> tmp = stops.earliestReachableStopAfter(currentTime);
            currentStop = tmp.getKey();
            if(currentStop == null) return null;
            currentTime = tmp.getValue();
        }

        while(!currentStop.equals(from)){
            Map.Entry<Time, LineName> tmp = stops.getReachableAt(currentStop);
            result.addFirst(new Triplet<>(currentStop,tmp.getKey(),tmp.getValue()));
            currentStop = lines.updateCapacityAndGetPreviousStop(tmp.getValue(),currentStop, tmp.getKey());
        }
        result.addFirst(new Triplet<>(from,when,null));
        stops.clean();
        lines.clean();
        return result;
    }
}
