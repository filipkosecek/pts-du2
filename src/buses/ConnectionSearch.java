package buses;

import java.util.Map;

public class ConnectionSearch {
    private final StopsInterface stops;
    private final LinesInterface lines;
    
    //tuto by som zmenila konstruktor na factory, lebo aj lines a aj stops mas na factory, tak by si to rovno poslal tam
    //davalo by to podla mna vacsi zmysel
    //EDIT: mne sa to zdalo ako odlahcenie ConnectionSearch pretoze stops aj lines maju referenciu na prislusnu factory a tym padom ConnectionSearch
    //nemusi vytvaranie stopiek a liniek vobec riesit vzdy si len vypyta potrebne informacie
    
    public ConnectionSearch(StopsInterface stops, LinesInterface lines){
        this.stops = stops;
        this.lines = lines;
    }

    //nechces si ten list obalit do triedy ConnectionData? lebo tak by to bolo cistejsie a na prvy pohlad by bolo vidno co to je, ale to len detail :D
    //EDIT: obalil som vysledny list do triedy ConnectionData
    
    public ConnectionData search(StopName from, StopName to, Time when){
        ConnectionData result = new ConnectionData();
        stops.resetReachable();
        stops.setStartingStop(from, when);
        StopName currentStop = from;
        Time currentTime = new Time(when.getTime());
        while(!currentStop.equals(to)){
            lines.updateReachable(stops.getLines(currentStop),currentTime, currentStop);
            Map.Entry<StopName,Time> tmp = stops.earliestReachableStopAfter(currentTime);
            currentStop = tmp.getKey();
            if(currentStop == null) return null;
            currentTime = tmp.getValue();
        }

        while(!currentStop.equals(from)){
            Map.Entry<Time, LineName> tmp = stops.getReachableAt(currentStop);
            result.addFirst(new Triplet<>(currentStop,tmp.getKey(),tmp.getValue()));
            currentStop = lines.updateCapacityAndGetPreviousStop(tmp.getValue(),currentStop, tmp.getKey());
        }
        Map.Entry<Time, LineName> tmp = stops.getReachableAt(currentStop);
        result.addFirst(new Triplet<>(currentStop,tmp.getKey(), tmp.getValue()));
        stops.clean();
        lines.clean();
        return result;
    }
}
