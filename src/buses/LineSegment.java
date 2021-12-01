package buses;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class LineSegment {
    protected final int capacity;
    protected Map<Time,Integer> numberOfPassengers;
    protected final LineName lineName;
    protected final TimeDiff timeToNextStop;
    protected final StopName nextStop;
    protected final StopsInterface stops;

    public LineSegment(int capacity, LineName lineName, StopName nextStop, TimeDiff timeToNextStop, StopsInterface stops){
        this.capacity = capacity;
        numberOfPassengers = new HashMap<>();
        this.lineName = lineName;
        this.timeToNextStop = timeToNextStop;
        this.nextStop = nextStop;
        this.stops = stops;
    }

    public Map.Entry<Time,StopName> nextStop(Time startTime){
        return new AbstractMap.SimpleEntry<>(new Time(startTime.getTime() + timeToNextStop.getTimeDiff()), nextStop);
    }

    public StopName nextStop(){
        return nextStop;
    }

    public Triplet<Time,StopName,Boolean> nextStopAndUpdateReachable(Time startTime){
        Time tmp = new Time(startTime.getTime() + timeToNextStop.getTimeDiff());
        if(numberOfPassengers.get(startTime) != null && numberOfPassengers.get(startTime) >= capacity) return new Triplet<>(tmp, nextStop,false);
        stops.getStopByName(nextStop).updateReachableAt(new Time(startTime.getTime() + timeToNextStop.getTimeDiff()), lineName);
        return new Triplet<>(tmp, nextStop,true);
    }

    public void incrementCapacity(Time startTime){
        if(!numberOfPassengers.containsKey(startTime)) numberOfPassengers.put(startTime,1);
        else numberOfPassengers.put(startTime,numberOfPassengers.get(startTime) + 1);
    }
}
