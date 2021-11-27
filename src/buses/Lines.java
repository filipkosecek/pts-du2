package buses;

import java.util.Map;
import java.util.ArrayList;

public class Lines implements LinesInterface{
    private Map<LineName,Line> lines;

    public Lines(Map<LineName,Line> lines){
        this.lines = lines;
    }

    @Override
    public void updateReachable(ArrayList<LineName> lines, Time time, StopName stopName){
        for(LineName line : lines){
            this.lines.get(line).updateReachable(time,stopName);
        }
    }

    @Override
    public StopName updateCapacityAndGetPreviousStop(LineName lineName, StopName stop, Time time){
        return lines.get(lineName).updateCapacityAndGetPreviousStop(stop, time);
    }

    @Override
    public void clean(){
        for(Line line : lines.values()){
            line.clean();
        }
    }
}
