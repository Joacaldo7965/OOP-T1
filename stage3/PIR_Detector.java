
public class PIR_Detector extends Sensor {
    float dir_angle, sens_angle, sens_range;

    public PIR_Detector(float dir_angle, float sens_angle, float sens_range){
        this.dir_angle = dir_angle;
        this.sens_angle = sens_angle;
        this.sens_range = sens_range;
    }

    public detectPeople(){
        
    }
}
