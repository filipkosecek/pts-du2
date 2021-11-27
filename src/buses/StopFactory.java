package buses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StopFactory {
    private final HashMap<StopName, ArrayList<LineName>> map;

    public StopFactory(Map<StopName, ArrayList<LineName>> input){
        map = new HashMap<>(input);
    }

    public Stop createStop(StopName stopName){
        return new Stop(stopName, map.get(stopName));
    }
}
