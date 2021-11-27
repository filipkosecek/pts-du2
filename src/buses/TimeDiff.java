package buses;

public class TimeDiff {
    private int timeDiff;

    public TimeDiff(int timeDiff){
        this.timeDiff = timeDiff;
    }

    public int getTimeDiff(){
        return timeDiff;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof TimeDiff)) return false;
        return timeDiff == ((TimeDiff) o).getTimeDiff();
    }
}
