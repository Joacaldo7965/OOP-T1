public class Door {
    public Door () {
        magneticSensor = new MagneticSensor();
        // TODO
    }
    {
        id = nextId++;
    }
    public void open() {
        // TODO
    }
    public void close() {
        // TODO
    }
    public String getHeader(){
        return "d"+id;
    }
    public int getState(){
        // TODO
        return 0;
    }

    private MagneticSensor magneticSensor;
    private State state;
    private final int id;
    private static int nextId;
    static {
        nextId = 0;
    }
}
