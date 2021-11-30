package buses;

import java.util.Objects;

public class Time {
    private int time;

    public Time(int time){
        this.time = time;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int newTime){
        time = newTime;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Time)) return false;
        return time == ((Time) o).getTime();
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }

    @Override
    public String toString(){
        return Integer.toString(time);
    }
}
