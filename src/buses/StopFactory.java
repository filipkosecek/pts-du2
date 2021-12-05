package buses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// preco si si nedal v tych factories createStop a createLine dokopy? co ked ti niekto zavola stopku z pamate a line z databazy? 
// Ako asi by to robilo potom na urovni logiky co ma, ale nemalo by byt zaistene aby to bolo jednotne? lebo ja som to tak vnimala :D

public class StopFactory implements AbstractStopFactory{
    private final HashMap<StopName, ArrayList<LineName>> stops;

    public StopFactory(Map<StopName, ArrayList<LineName>> stops){
        this.stops = new HashMap<>(stops);
    }

    @Override
    public Stop createStop(StopName stop){
        return new Stop(stop, stops.get(stop));
    }
}
