public class Door {
    private MagneticSensor magneticSensor;
    private State state;
    private final int id;
    private static int nextId;
    static {
        nextId = 0;
    }

    public Door () {
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
        return "d"+id;
    }

    public State getState(){
        if(magneticSensor.getState() == SwitchState.OPEN)
            return State.OPEN;
        return State.CLOSE;
    }

    
}
