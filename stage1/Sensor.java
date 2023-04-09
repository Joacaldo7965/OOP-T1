public class Sensor {
    public Sensor(){
        this(SwitchState.OPEN);
    }
    public Sensor(SwitchState s){
        // TODO
    }
    public SwitchState getState(){
        // TODO
        return null;
    }
    protected void setState(SwitchState s) {
        // TODO
    }
    public String toString(){
        if (state== SwitchState.CLOSE)
            return "1";
        else
            return "0";
    }
    private SwitchState state;
}
