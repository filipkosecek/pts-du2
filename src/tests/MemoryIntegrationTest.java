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
        List<Triplet<StopName,Time,LineName>> result = cs.search(new StopName("a"),new StopName("b"),new Time(2));
        assertEquals("a", result.get(0).getX().getStopName());
        assertEquals(new Time(2), result.get(0).getY());
        assertNull(result.get(0).getZ());

        assertEquals("b", result.get(1).getX().getStopName());
        assertEquals(new Time(10), result.get(1).getY());
        assertEquals(new LineName("1"), result.get(1).getZ());
    }
}
