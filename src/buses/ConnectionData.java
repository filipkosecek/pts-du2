package buses;

import java.util.ArrayList;

public class ConnectionData {
    private final ArrayList<Triplet<StopName, Time, LineName>> searchResult;

    public ConnectionData(StopName stop, Time time, LineName line){
        searchResult = new ArrayList<>();
    }

    public void add(Triplet<StopName,Time,LineName> element){
        //searchResult.
    }

    public Triplet<StopName,Time,LineName> get(int index){
        return searchResult.get(index);
    }
}
