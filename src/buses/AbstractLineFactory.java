package buses;

public interface AbstractLineFactory {
    Line createLine(LineName lineName);
}
