public class Door extends Frame{
    private MagneticSensor magneticSensor;
    private final int id;
    private static int nextId;
    static {
        nextId = 0;
    }

    public Door () {
        magneticSensor = new MagneticSensor();
    }
    {
        id = nextId++;
    }

    @Override
    public void open() {
        magneticSensor.moveMagnetAwayFromSwitch();
    }

    public void close() {
        magneticSensor.putMagnetNearSwitch();
    }

    public String getHeader(){
        return "d"+id;
    }    
}
