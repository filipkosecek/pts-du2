package buses;

public class LineName {
    private String name;

    public LineName(String name){
        this.name = name;
    }

    public String getLineName(){
        return name;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof LineName)) return false;
        return name.equals(((LineName) o).getLineName());
    }
}
