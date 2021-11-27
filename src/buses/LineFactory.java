package buses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LineFactory {
    private final HashMap<LineName, Map.Entry<StopName,ArrayList<Time>>> map;

    public LineFactory(Map<LineName, Map.Entry<StopName,ArrayList<Time>>> input){
        map = new HashMap<>(input);
    }

    public Line createLine(LineName lineName){
        return null;
        // return new Line(lineName, map.get(lineName).getKey(),map.get(lineName).getValue())
    }
}
