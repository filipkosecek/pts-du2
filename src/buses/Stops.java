package buses;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class Stops {
    private final ArrayList<Stop> stops;
    private StopName startingStop;
    private Time startingTime;
    private final PriorityQueue<Stop> queue;

    public Stops(ArrayList<Stop> stops){
        this.stops = stops;
        queue = new PriorityQueue<>();
    }

    public void setStartingStop(StopName startingStop, Time startingTime){
        this.startingStop = startingStop;
        this.startingTime = startingTime;
    }

    public ArrayList<LineName> getLines(StopName stop){
        for(Stop s : stops){
            if(s.getStopName().equals(stop)){
                return s.getLines();
            }
        }
        throw new NoSuchElementException();
    }

    public Map.Entry<Time, LineName> getReachableAt(StopName stop){
        for(Stop s : stops){
            if(s.getStopName().equals(stop)){
                return s.getReachableAt();
            }
        }
        throw new NoSuchElementException();
    }
}
