package buses;

import java.util.*;

public class Stops implements StopsInterface{
    private final AbstractStopFactory factory;
    private final HashMap<StopName, Stop> stops;

    public Stops(AbstractStopFactory factory){
        this.factory = factory;
        stops = new HashMap<>();
    }

    @Override
    public Stop getStopByName(StopName stopName){
        if(!stops.containsKey(stopName)){
            stops.put(stopName, factory.createStop(stopName));
        }
        return stops.get(stopName);
    }

    public void setStartingStop(StopName startingStop, Time startingTime){
        if(!stops.containsKey(startingStop)){
            stops.put(startingStop, factory.createStop(startingStop));
        }
        stops.get(startingStop).setReachableAt(startingTime);
        stops.get(startingStop).setReachableVia(null);
    }

    // toto si preco cez try, nie ako setStartingStop? len ma zaujima :D
    public ArrayList<LineName> getLines(StopName stop){
        try{
            return stops.get(stop).getLines();
        }catch(NoSuchElementException e){
            stops.put(stop, factory.createStop(stop));
            return stops.get(stop).getLines();
        }
    }

    //niekto sa zbavil Optional :D
    public Map.Entry<Time, LineName> getReachableAt(StopName stop){
        try{
            return stops.get(stop).getReachableAt();
        }catch(NoSuchElementException e){
            return null;
        }
    }

    public void resetReachable(){
        for(Stop s : stops.values()){
            s.setReachableAt(null);
            s.setReachableVia(null);
        }
    }

    public Map.Entry<StopName, Time> earliestReachableStopAfter(Time time){
        Time earliest = null;
        StopName earliestStop = null;
        for(StopName stopName : stops.keySet()){
            Time current = stops.get(stopName).getReachableAt().getKey(); 
            if(current.getTime() > time.getTime() &&
                    (earliest == null || current.getTime() < earliest.getTime())){
                earliest = new Time(current.getTime());
                earliestStop = stopName;
            }
        }

        if(earliest == null) return null;
        return new AbstractMap.SimpleEntry<>(earliestStop, earliest);
    }

    public void clean(){
        stops.clear();
    }
}
