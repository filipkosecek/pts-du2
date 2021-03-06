package buses;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class Stop {
    private final StopName name;
    private final ArrayList<LineName> lines;
    private Time reachableAt;
    private LineName reachableVia;

    public Stop(StopName name, ArrayList<LineName> lines){
        this.name = name;
        this.lines = lines;
    }

    public StopName getStopName(){
        return name;
    }

    public ArrayList<LineName> getLines(){
        return lines;
    }

    public void updateReachableAt(Time time, LineName line){
        if(reachableAt == null || time.getTime() < reachableAt.getTime()){
            reachableAt = time;
            reachableVia = line;
        }
    }

    public void setReachableAt(Time time){
        reachableAt = time;
    }

    public void setReachableVia(LineName lineName){
        reachableVia = lineName;
    }

    //toto podla mna strasne zavadza nazvom, ja som to menila z navrhu. (moj koment :D)
    public Map.Entry<Time,LineName> getReachableAt(){
        return new AbstractMap.SimpleEntry<>(reachableAt, reachableVia);
    }
}
