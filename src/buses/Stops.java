package buses;

import java.util.*;

public class Stops implements StopsInterface{
    private final Factory factory;
    private final HashMap<StopName, Stop> stops;
    private StopName startingStop;
    private Time startingTime;

    public Stops(Factory factory){
        this.factory = factory;
        stops = new HashMap<>();
    }

    @Override
    public void setStartingStop(StopName startingStop, Time startingTime){
        if(!stops.containsKey(startingStop)){
            stops.put(startingStop, factory.createStop(startingStop));
        }
        this.startingStop = startingStop;
        this.startingTime = startingTime;
        stops.get(startingStop).setReachableAt(startingTime);
        stops.get(startingStop).setReachableVia(null);
    }

    @Override
    public ArrayList<LineName> getLines(StopName stop){
        try{
            return stops.get(stop).getLines();
        }catch(NoSuchElementException e){
            stops.put(stop, factory.createStop(stop));
            return stops.get(stop).getLines();
        }
    }

    @Override
    public Map.Entry<Time, LineName> getReachableAt(StopName stop){
        try{
            return stops.get(stop).getReachableAt();
        }catch(NoSuchElementException e){
            return null;
        }
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
