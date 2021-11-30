package tests;

import buses.*;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class MemoryIntegrationTest {
    private ConnectionSearch cs;
    private FakeLineFactory fakeLineFactory;
    private FakeStopFactory fakeStopFactory;
    private StopsInterface stopsInterface;
    private LinesInterface linesInterface;

    public void setup(){
        fakeStopFactory = new FakeStopFactory();
        stopsInterface = new Stops(fakeStopFactory);
        fakeLineFactory = new FakeLineFactory(stopsInterface);
        linesInterface = new Lines(fakeLineFactory);
        cs = new ConnectionSearch(stopsInterface, linesInterface);
    }

    @Test
    public void test(){
        setup();
        List<Triplet<StopName,Time,LineName>> result = cs.search(new StopName("a"),new StopName("c"),new Time(1));
        assertEquals("a", result.get(0).getX().getStopName());
        assertEquals(new Time(1), result.get(0).getY());
        assertNull(result.get(0).getZ());

        assertEquals("b",result.get(1).getX().getStopName());
        assertEquals(new Time(7), result.get(1).getY());
        assertEquals(new LineName("1"), result.get(1).getZ());

        assertEquals("d", result.get(2).getX().getStopName());
        assertEquals(new Time(13), result.get(2).getY());
        assertEquals(new LineName("2"), result.get(2).getZ());

        assertEquals("c", result.get(3).getX().getStopName());
        assertEquals(new Time(18), result.get(3).getY());
        assertEquals(new LineName("2"), result.get(3).getZ());
    }
}
