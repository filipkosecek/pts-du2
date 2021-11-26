package buses;

import java.util.ArrayList;
import java.util.Map;

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

    public void updateReachable(Time time, StopName stopName){
        //mozno osetrit ci nevybehne mimo pola -> vyhodit vynimku
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
        int timeFromStartingStop = 0;
        while(lineSegmentIndex < lineSegments.size()){
            Triplet<Time,StopName,Boolean> triplet = lineSegments.get(lineSegmentIndex).nextStopAndUpdateReachable(new Time(timeFromStartingStop));
            timeFromStartingStop = triplet.getX().getTime();
            ++lineSegmentIndex;
        }
    }
}
