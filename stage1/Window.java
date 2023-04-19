public class Window {
    private MagneticSensor magneticSensor;
    private State state;
    private final int id;
    private static int nextId=0;
    
    public Window() {
        magneticSensor = new MagneticSensor();
        state = State.CLOSE;
    }
    {
        id = nextId++;
    }

    public void open() {
        magneticSensor.moveMagnetAwayFromSwitch();
        state = State.OPEN;
    }

    public void close() {
        magneticSensor.putMagnetNearSwitch();
        state = State.CLOSE;
    }

    public String getHeader(){
        return "w"+id;
    }

    public State getState(){
        if(magneticSensor.getState() == SwitchState.OPEN)
            return State.OPEN;
        return State.CLOSE;
    }
    
}
