package buses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
