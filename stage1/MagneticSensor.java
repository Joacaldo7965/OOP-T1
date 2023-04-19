public class MagneticSensor extends Sensor {
    public MagneticSensor(){}
    public void moveMagnetAwayFromSwitch() {
        // TODO
        //setState(....);
        setState(SwitchState.OPEN);
    }
    public void putMagnetNearSwitch() {
        // TODO
        setState(SwitchState.CLOSE);
    }
}
