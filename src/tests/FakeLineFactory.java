package tests;

import buses.*;

import java.util.ArrayList;

public class FakeLineFactory implements AbstractLineFactory {
    private StopsInterface stopsInterface;

    public FakeLineFactory(StopsInterface stopsInterface){
        this.stopsInterface = stopsInterface;
    }

    @Override
    public Line createLine(LineName lineName){
        ArrayList<Time> startingTimes = new ArrayList<>();
        ArrayList<LineSegment> lineSegments = new ArrayList<>();
        if(lineName.getLineName().equals("1")){
            lineSegments.add(new LineSegment(25,lineName,new StopName("b"),new TimeDiff(5),stopsInterface));
            startingTimes.add(new Time(1));
            startingTimes.add(new Time(5));
            startingTimes.add(new Time(8));
            return new Line(lineName,new StopName("a"),startingTimes,lineSegments);
        }
        if(lineName.getLineName().equals("2")) {
            startingTimes.add(new Time(2));
            startingTimes.add(new Time(11));
            startingTimes.add(new Time(21));
            lineSegments.add(new LineSegment(12, lineName, new StopName("c"), new TimeDiff(4), stopsInterface));
            return new Line(lineName,new StopName("a"),startingTimes,lineSegments);
        }
        throw new RuntimeException();
    }
}
