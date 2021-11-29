package buses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Factory implements AbstractFactory{
    private final HashMap<StopName, ArrayList<LineName>> stops;
    private final HashMap<LineName, Map.Entry<StopName,ArrayList<Time>>> lines;
    private final HashMap<LineName,ArrayList<Triplet<Stop,TimeDiff,Integer>>> lineSegments;

    public Factory(Map<StopName, ArrayList<LineName>> stops, Map<LineName, Map.Entry<StopName,ArrayList<Time>>> lines, Map<LineName,ArrayList<Triplet<Stop,TimeDiff,Integer>>> lineSegment){
        this.stops = new HashMap<>(stops);
        this.lines = new HashMap<>(lines);
        this.lineSegments = new HashMap<>(lineSegment);
    }

    @Override
    public Stop createStop(StopName stop){
        return new Stop(stop, stops.get(stop));
    }

    @Override
    public Line createLine(LineName lineName){
        ArrayList<Triplet<Stop,TimeDiff,Integer>> tmp = lineSegments.get(lineName);
        ArrayList<LineSegment> ls = new ArrayList<>(tmp.size());
        for(Triplet<Stop,TimeDiff,Integer> triplet : tmp){
            ls.add(new LineSegment(triplet.getZ(), lineName, triplet.getX(), triplet.getY()));
        }
        return new Line(lineName,lines.get(lineName).getKey(),lines.get(lineName).getValue(),ls);
    }
}
