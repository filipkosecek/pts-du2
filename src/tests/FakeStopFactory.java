package tests;

import buses.*;

import java.util.ArrayList;

public class FakeStopFactory implements AbstractStopFactory {
    @Override
    public Stop createStop(StopName stop){
        ArrayList<LineName> lines = new ArrayList<>();
        if(stop.getStopName().equals("a")) {
            lines.add(new LineName("1"));
            lines.add(new LineName("2"));
            return new Stop(stop, lines);
        }
        if(stop.getStopName().equals("b")){
            lines.add(new LineName("1"));
            return new Stop(stop, lines);
        }
        if(stop.getStopName().equals("c")) {
            lines.add(new LineName("2"));
            return new Stop(stop, lines);
        }
        throw new RuntimeException();
    }
}
