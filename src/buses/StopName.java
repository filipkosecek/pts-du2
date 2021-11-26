package buses;

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
}
