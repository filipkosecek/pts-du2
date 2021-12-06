package buses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// preco si si nedal v tych factories createStop a createLine dokopy? co ked ti niekto zavola stopku z pamate a line z databazy? 
// Ako asi by to robilo potom na urovni logiky co ma, ale nemalo by byt zaistene aby to bolo jednotne? lebo ja som to tak vnimala :D
//EDIT: zdalo sa mi to tak lepsie pretoze Lines aj Stops maju referenciu na prislusnu factory a navyse LineFactory ma referenciu na Stops
//pretoze lineSegment ma len StopName a potom si od Stops vypyta prislusnu Stop
//cize ak by som to spojil do jednej factory tak ta by musela mat referenciu na stops a stops zas referenciu na factory co by bolo podla mna robilo problemy
//hlavne pri injectovani
//ja som zo zadania pochopil ze teoreticky by sa mohla volat databazova verzia stops a memory verzia lines, v takom pripade by to ale
//malo fungovat ak databaza a memory verzia lines budu obsahovat vsetko potrebne

public class StopFactory implements AbstractStopFactory{
    private final HashMap<StopName, ArrayList<LineName>> stops;

    public StopFactory(Map<StopName, ArrayList<LineName>> stops){
        this.stops = new HashMap<>(stops);
    }

    @Override
    public Stop createStop(StopName stop){
        return new Stop(stop, stops.get(stop));
    }
}
