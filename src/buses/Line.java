package buses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.List;

public class Line {
    private final LineName lineName;
    private final StopName firstStop;
    private final ArrayList<Time> startingTimes;
    private final ArrayList<LineSegment> lineSegments;

    public Line(LineName lineName, StopName firstStop, ArrayList<Time> startingTimes, ArrayList<LineSegment> lineSegments){
        this.lineName = lineName;
        this.firstStop = firstStop;
        this.startingTimes = startingTimes; //startingTimes must be sorted
        this.lineSegments = lineSegments; //lineSegments must be sorted
    }

    public StopName getFirstStop(){
        return firstStop;
    }

    public List<LineSegment> getLineSegments(){
        return Collections.unmodifiableList(lineSegments);
    }

    public void updateReachable(Time time, StopName stopName){
        if(startingTimes.size() <= 0) throw new RuntimeException();
        Time tmp = startingTimes.get(0);
        int timeToDesiredStop = 0;
        int lineSegmentIndex = 0;
        if(!firstStop.getStopName().equals(stopName.getStopName())) {
            for (LineSegment lineSegment : lineSegments) {
                Map.Entry<Time, StopName> pair = lineSegment.nextStop(tmp);
                ++lineSegmentIndex;
                if (pair.getValue().getStopName().equals(stopName.getStopName())) {
                    timeToDesiredStop = pair.getKey().getTime() - tmp.getTime();
                    break;
                }
            }
        }
        for(Time t : startingTimes) {
            if (t.getTime() + timeToDesiredStop >= time.getTime()) {
                tmp = new Time(t.getTime());
                break;
            }
        }

        if(lineSegmentIndex == 0) ++lineSegmentIndex;
        int timeFromStartingStop = tmp.getTime();
        while(lineSegmentIndex < lineSegments.size()){
            Triplet<Time,StopName,Boolean> triplet =
                    lineSegments.get(lineSegmentIndex).nextStopAndUpdateReachable(new Time(timeFromStartingStop));
            timeFromStartingStop = triplet.getX().getTime();
            ++lineSegmentIndex;
        }
    }

    public StopName updateCapacityAndGetPreviousStop(StopName stop, Time time){
        StopName prevStop = firstStop;
        LineSegment lastSegment = null;
        for(LineSegment ls : lineSegments){
            if(ls.nextStop().equals(stop)){
                lastSegment = ls;
                break;
            }
            prevStop = ls.nextStop();
        }
        if(lastSegment == null) throw new RuntimeException();
        lastSegment.incrementCapacity(time);
        return prevStop;
    }

    public void clean(){
        for(LineSegment ls : lineSegments){
            ls.clean();
        }
    }
}
