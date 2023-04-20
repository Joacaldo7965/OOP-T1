public class Door extends Frame{
    private MagneticSensor magneticSensor;
    private final int id;
    private static int nextId=0;

    public Door () {
        magneticSensor = new MagneticSensor();
    }
    {
        id = nextId++;
    }

    @Override
    public void open() {
        super.open();
        magneticSensor.moveMagnetAwayFromSwitch();
    }

    @Override
    public void close() {
        super.close();
        magneticSensor.putMagnetNearSwitch();
    }

    public String getHeader(){
        return "\td"+id;
    }    

    public Sensor getSensor(){
        return magneticSensor;
    }
}
