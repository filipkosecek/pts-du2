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
        if(startingTimes.size() <= 0) throw new RuntimeException(); //moze byt velkost mensia ako nula? :D
        Time firstStartingTime = startingTimes.get(0);
        Time tmp = new Time(firstStartingTime.getTime());
        int timeToDesiredStop = 0;
        int lineSegmentIndex = 0;
        if(!firstStop.getStopName().equals(stopName.getStopName())) {
            for (LineSegment lineSegment : lineSegments) {
                Map.Entry<Time, StopName> pair = lineSegment.nextStop(tmp);
                ++lineSegmentIndex;
                if (pair.getValue().getStopName().equals(stopName.getStopName())) {
                    timeToDesiredStop = pair.getKey().getTime() - firstStartingTime.getTime();
                    break;
                }
                tmp = new Time(pair.getKey().getTime());
            }
        }
        int optimalStartingTime = 0;
        for(Time t : startingTimes) {
            if (t.getTime() + timeToDesiredStop >= time.getTime()) {
                optimalStartingTime = t.getTime();
                break;
            }
        }

        int timeFromStartingStop = optimalStartingTime + timeToDesiredStop;
        while(lineSegmentIndex < lineSegments.size()){
            Triplet<Time,StopName,Boolean> triplet =
                    lineSegments.get(lineSegmentIndex).nextStopAndUpdateReachable(new Time(timeFromStartingStop));
            if(!triplet.getZ()) break;
            timeFromStartingStop = triplet.getX().getTime(); //cenim vlastne metody getXYZ, ja mam ABC :DDDD 
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
}
