package buses;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class LineSegment {
    private final int capacity;
    private final Map<Time,Integer> numberOfPassengers;
    private final LineName lineName;
    private final TimeDiff timeToNextStop;
    private final Stop nextStop;

    public LineSegment(int capacity, LineName lineName, Stop nextStop, TimeDiff timeToNextStop){
        this.capacity = capacity;
        numberOfPassengers = new HashMap<>();
        this.lineName = lineName;
        this.timeToNextStop = timeToNextStop;
        this.nextStop = nextStop;
    }

    public Map.Entry<Time,StopName> nextStop(Time startTime){
        return new AbstractMap.SimpleEntry<>(new Time(startTime.getTime() + timeToNextStop.getTimeDiff()), nextStop.getStopName());
    }

    public StopName nextStop(){
        return nextStop.getStopName();
    }

    public Triplet<Time,StopName,Boolean> nextStopAndUpdateReachable(Time startTime){
        Time tmp = new Time(startTime.getTime() + timeToNextStop.getTimeDiff());
        if(numberOfPassengers.get(startTime) >= capacity) return new Triplet<>(tmp, nextStop.getStopName(),false);
        nextStop.updateReachableAt(new Time(startTime.getTime() + timeToNextStop.getTimeDiff()), lineName);
        return new Triplet<>(tmp, nextStop.getStopName(),true);
    }

    public void incrementCapacity(Time startTime){
        numberOfPassengers.put(startTime,numberOfPassengers.get(startTime) + 1);
    }
}
