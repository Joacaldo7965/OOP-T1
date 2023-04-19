public class Window extends Frame{
    private MagneticSensor magneticSensor;
    private final int id;
    private static int nextId=0;
    
    public Window() {
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
        return "w"+id;
    }
}
