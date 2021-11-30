package tests;

import buses.*;

import java.util.*;

import org.junit.Test;
import static org.junit.Assert.*;

public class LineFactoryTest {
    private LineFactory factory;
    private StopsInterface stopsInterface;

    public void setup(){
        ArrayList<LineName> tmp = new ArrayList<>();
        tmp.add(new LineName("1"));
        HashMap<StopName,ArrayList<LineName>> stops = new HashMap<>();
        stops.put(new StopName("a"), new ArrayList<>(tmp));
        stops.put(new StopName("b"), new ArrayList<>(tmp));
        tmp.clear();
        stopsInterface = new Stops(new StopFactory(stops));

        HashMap<LineName, Map.Entry<StopName,ArrayList<Time>>> lines = new HashMap<>();
        ArrayList<Time> times = new ArrayList<>();
        times.add(new Time(5));
        lines.put(new LineName("1"), new AbstractMap.SimpleEntry<>(new StopName("a"), new ArrayList<>(times)));

        ArrayList<Triplet<StopName, TimeDiff, Integer>> lineSegments = new ArrayList<>();
        lineSegments.add(new Triplet<>(new StopName("b"),new TimeDiff(5),25));
        HashMap<LineName, ArrayList<Triplet<StopName,TimeDiff,Integer>>> ls = new HashMap<>();
        ls.put(new LineName("1"), new ArrayList<>(lineSegments));
        factory = new LineFactory(lines, ls, stopsInterface);
    }

    @Test
    public void testCreateLine(){
        setup();
        Line line = factory.createLine(new LineName("1"));
        assertEquals(new StopName("a"), line.getFirstStop());
        for(LineSegment ls : line.getLineSegments()){
            assertEquals(new StopName("b"), ls.nextStop());
        }
    }
}
