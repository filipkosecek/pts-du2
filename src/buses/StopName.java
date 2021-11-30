package buses;

import java.util.Objects;

public class StopName {
    private String name;

    public StopName(String name){
        this.name = name;
    }

    public String getStopName(){
        return name;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof StopName)) return false;
        return name.equals(((StopName) o).getStopName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString(){
        return name;
    }
}
