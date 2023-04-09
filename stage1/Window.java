public class Window {
    private MagneticSensor magneticSensor;
    private State state;
    private final int id;
    private static int nextId=0;
    
    public Window() {
        // TODO
    }
    {
        id = nextId++;
    }
    public void open() {
        // TODO
        return;
    }
    public void close() {
        // TODO
        return;
    }
    public String getHeader(){
        return "w"+id;
    }
    public int getState(){
        // TODO
        return 0;
    }
    
}
