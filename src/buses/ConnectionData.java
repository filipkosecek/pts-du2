package buses;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ConnectionData {
    private final LinkedList<Triplet<StopName,Time,LineName>> data = new LinkedList<>();

    public void addFirst(Triplet<StopName,Time,LineName> element){
        data.addFirst(element);
    }

    public List<Triplet<StopName,Time,LineName>> getData(){
        return Collections.unmodifiableList(data);
    }
}
