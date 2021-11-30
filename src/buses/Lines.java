package buses;

import java.util.Map;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Lines implements LinesInterface{
    private AbstractLineFactory factory;
    private Map<LineName,Line> lines;

    public Lines(AbstractLineFactory factory){
        this.factory = factory;
    }

    @Override
    public void updateReachable(ArrayList<LineName> lines, Time time, StopName stopName){
        for(LineName line : lines){
            try{
                this.lines.get(line);
            }catch(NoSuchElementException e){
                this.lines.put(line, factory.createLine(line));
                this.lines.get(line);
            }
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
