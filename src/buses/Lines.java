package buses;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Lines implements LinesInterface{
    private final AbstractLineFactory factory;
    private final Map<LineName,Line> lines;

    public Lines(AbstractLineFactory factory){
        this.factory = factory;
        lines = new HashMap<>();
    }

    @Override
    public void updateReachable(ArrayList<LineName> lines, Time time, StopName stopName){
        for(LineName line : lines){
            if(this.lines.get(line) != null){
                this.lines.get(line).updateReachable(time, stopName);
            }else{
                Line tmp = factory.createLine(line);
                this.lines.put(line, tmp);
                tmp.updateReachable(time, stopName);
            }
        }
    }

    @Override
    public StopName updateCapacityAndGetPreviousStop(LineName lineName, StopName stop, Time time){
        return lines.get(lineName).updateCapacityAndGetPreviousStop(stop, time);
    }

    @Override
    public void clean(){
        lines.clear();
    }
}
