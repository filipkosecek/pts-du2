package buses;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(timeDiff);
    }
}
