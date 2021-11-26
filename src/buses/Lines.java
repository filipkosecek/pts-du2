package buses;

import java.util.ArrayList;

public class Lines {
    private ArrayList<Line> lines;

    public Lines(ArrayList<Line> lines){
        this.lines = lines;
    }

    public void updateReachable(ArrayList<Line> lines, Time time, StopName stopName){
        for(Line line : lines){
            line.updateReachable(time, stopName);
        }
    }
}
