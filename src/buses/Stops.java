package buses;

import java.util.*;

public class Stops implements StopsInterface{
    private final HashMap<StopName, Stop> stops;
    private StopName startingStop;
    private Time startingTime;

    public Stops(){
        this.stops = new HashMap<>();
    }

    @Override
    public void setStartingStop(StopName startingStop, Time startingTime){
        this.startingStop = startingStop;
        this.startingTime = startingTime;
        stops.get(startingStop).setReachableAt(startingTime);
        stops.get(startingStop).setReachableVia(null);
    }

    @Override
    public ArrayList<LineName> getLines(StopName stop){
        return stops.get(stop).getLines();
    }

    @Override
    public Map.Entry<Time, LineName> getReachableAt(StopName stop){
        return stops.get(stop).getReachableAt();
    }

    @Override
    public void resetReachable(){
        for(Stop s : stops.values()){
            s.setReachableAt(null);
            s.setReachableVia(null);
        }
    }

    @Override
    public Map.Entry<StopName, Time> earliestReachableStopAfter(Time time){
        //treba checknut ci tieto hodnoty nie su null
        Map.Entry<StopName, Time> earliest = null;
        for(Stop s : stops.values()){
            Map.Entry<StopName, Time> tmp = new AbstractMap.SimpleEntry<>(s.getStopName(),s.getReachableAt().getKey());
            if(tmp.getKey() == null || tmp.getValue() == null) continue;
            if(earliest == null && tmp.getValue().getTime() > time.getTime()){
                earliest = tmp;
            }else if(tmp.getValue().getTime() > time.getTime() && tmp.getValue().getTime() < earliest.getValue().getTime()){
                earliest = new AbstractMap.SimpleEntry<>(s.getStopName(), s.getReachableAt().getKey());
            }
        }
        return null;
    }

    @Override
    public void clean(){
        for(Stop s : stops.values()){
            s.clean();
        }
    }
}
