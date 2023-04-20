import java.util.ArrayList;


public class PIR_Detector extends Sensor {
    private float x, y;
    private float dir_angle, sens_angle, sens_range;

    private final int id;
    private static int nextId=0;

    public PIR_Detector(float x, float y, float dir_angle, float sens_angle, float sens_range){
        this.x = x;
        this.y = y;
        this.dir_angle = dir_angle;
        this.sens_angle = sens_angle;
        this.sens_range = sens_range;
    }
    {
        id = nextId++;
    }

    public void activateSensor(){
        setState(SwitchState.OPEN);
    }

    public void deactivateSensor(){
        setState(SwitchState.CLOSE);
    }

    public boolean detectPeople(ArrayList<Person> people){
        for (int i = 0; i < people.size(); i++) {
            Person p = people.get(i);

            if(isPointInRange(p.getPosX(), p.getPosY())){
                activateSensor();
                return true;
            }
        }
        return false;
    }

    private boolean isPointInRange(float px, float py){
        // Distance squared
        float dx = px - x;
        float dy = py - y;
        float d2 = dx*dx + dy*dy;

        // If not in range return false
        if(d2 > sens_range*sens_range)
            return false;
        
        // Angle validation  *considering dir_angle in the middle of sens_angle
        float gamma = (float) Math.toDegrees(Math.atan2(dy, dx));
        float dif = Math.abs(gamma - dir_angle);
        if(dif <= sens_angle/2)
            return true;
        
        return false;
    }

    public String getHeader(){
        return "\tPir"+id;
    }

    // DEBUG
    @Override
    public String toString(){
        return "PIR: (" + x + ", " + y + ") " + dir_angle + " " + sens_angle + " " + sens_range;
    }
}
