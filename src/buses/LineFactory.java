package buses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LineFactory implements AbstractLineFactory{
    private final HashMap<LineName, Map.Entry<StopName,ArrayList<Time>>> lines;
    private final HashMap<LineName, ArrayList<Triplet<StopName,TimeDiff,Integer>>> lineSegments;
    private final StopsInterface stopsInterface;

    public LineFactory(Map<LineName, Map.Entry<StopName,ArrayList<Time>>> lines,
                       Map<LineName, ArrayList<Triplet<StopName,TimeDiff,Integer>>> lineSegments,
                       StopsInterface stopsInterface){
        this.lines = new HashMap<>(lines);
        this.lineSegments = new HashMap<>(lineSegments);
        this.stopsInterface = stopsInterface;
    }

    @Override
    public Line createLine(LineName lineName){
        ArrayList<Triplet<StopName,TimeDiff,Integer>> tmp = lineSegments.get(lineName);
        ArrayList<LineSegment> ls = new ArrayList<>();
        for(Triplet<StopName,TimeDiff,Integer> triplet : tmp){
            ls.add(new LineSegment(triplet.getZ(), lineName, triplet.getX(), triplet.getY(), stopsInterface));
        }
        return new Line(lineName,lines.get(lineName).getKey(),lines.get(lineName).getValue(),ls);
    }
}
