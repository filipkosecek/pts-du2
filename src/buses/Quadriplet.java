package buses;

public class Quadriplet<X,Y,Z,W>{
    private X x;
    private Y y;
    private Z z;
    private W w;

    public Quadriplet(X x, Y y, Z z, W w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public X getX(){
        return x;
    }

    public Y getY(){
        return y;
    }

    public Z getZ(){
        return z;
    }

    public W getW(){
        return w;
    }
}
