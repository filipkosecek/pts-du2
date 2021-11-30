package tests;

import org.junit.Test;
import static org.junit.Assert.*;
import buses.*;

import java.util.*;

public class StopFactoryTest {
    private AbstractStopFactory factory;

    public void setup(){
        HashMap<StopName, ArrayList<LineName>> stops = new HashMap<>();
        ArrayList<LineName> tmp = new ArrayList<>();
        tmp.add(new LineName("1"));
        tmp.add(new LineName("2"));
        stops.put(new StopName("a"), new ArrayList<>(tmp));
        tmp.clear();
        tmp.add(new LineName("1"));
        stops.put(new StopName("b"), new ArrayList<>(tmp));
        tmp.clear();
        tmp.add(new LineName("2"));
        stops.put(new StopName("c"), new ArrayList<>(tmp));
        factory = new StopFactory(stops);
    }

    @Test
    public void testCreateStop(){
        setup();
        assertEquals(new StopName("a"), factory.createStop(new StopName("a")).getStopName());
        ArrayList<LineName> tmp = new ArrayList<>();
        tmp.add(new LineName("1"));
        tmp.add(new LineName("2"));
        assertEquals(tmp, factory.createStop(new StopName("a")).getLines());
        tmp.remove(1);
        assertEquals(tmp, factory.createStop(new StopName("b")).getLines());
        tmp.clear();
        tmp.add(new LineName("2"));
        assertEquals(tmp, factory.createStop(new StopName("c")).getLines());
    }
}
