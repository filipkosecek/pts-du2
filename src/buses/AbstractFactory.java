package buses;

public interface AbstractFactory {
    Stop createStop(StopName stop);
    Line createLine(LineName lineName);
}
