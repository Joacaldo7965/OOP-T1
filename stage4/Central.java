import java.util.ArrayList;

public class Central {

    // TODO: create class Zone ??
    // TODO: generalize to N zones
    private ArrayList<Sensor> zone0;
    private ArrayList<Sensor> zone1;
    private ArrayList<Sensor> zone2;

    // private boolean isZone0Armed;
    // private boolean isZone1Armed;
    // private boolean isZone2Armed;
    private boolean isNightModeActive;

    private boolean isArmed;
    private Siren siren;

    public Central(){
        zone0 = new ArrayList<Sensor>();
        zone1 = new ArrayList<Sensor>();
        zone2 = new ArrayList<Sensor>();

        isArmed = false;
        isNightModeActive = false;
        // isZone0Armed = false;
        // isZone1Armed = false;
        // isZone2Armed = false;
        
        siren = null;
    }

    public void arm() {
        ArrayList<Integer> zonesOpen = getOpenZones();
        if(zonesOpen.size() == 0){
            isArmed=true;
            
        }else{
            System.out.print("Zonas no cerradas:");
            for (int i = 0; i < zonesOpen.size(); i++) {
                System.out.print(" " + zonesOpen.get(i));
            }
            System.out.println();
        }
        
    }

    public void armNightMode(){
        ArrayList<Integer> zonesOpen = getOpenZones();
        if(zonesOpen.size() == 0){
            isArmed = true;
            isNightModeActive = true;
        }else if(zonesOpen.size() == 1 && zonesOpen.contains(2)){
            isArmed = true;
            isNightModeActive = true;
        }else{
            System.out.print("Zonas no cerradas:");
            for (int i = 0; i < zonesOpen.size(); i++) {
                int zone = zonesOpen.get(i);
                if(zone != 2)
                    System.out.print(" " + zone);
            }
            System.out.println();
        }
    }

    public ArrayList<Integer> getOpenZones(){
        ArrayList<Integer> zonesOpen = new ArrayList<Integer>();

        // Zone 0
        for (int i = 0; i < zone0.size(); i++) {
            if(zone0.get(i).getState() == SwitchState.OPEN){
                zonesOpen.add(0);
                break;
            }
        }
        // Zone 1
        for (int i = 0; i < zone1.size(); i++) {
            if(zone1.get(i).getState() == SwitchState.OPEN){
                zonesOpen.add(1);
                break;
            }
        }
        // Zone 2
        for (int i = 0; i < zone2.size(); i++) {
            if(zone2.get(i).getState() == SwitchState.OPEN){
                zonesOpen.add(2);
                break;
            }
        }
        return zonesOpen;
    }

    public void disarm() {
        isArmed = false;
        isNightModeActive = false;
    }

    public void setSiren(Siren s) {
        siren = s;
    }

    public void addNewSensor(int zone_index, Sensor s){
        // zone_index : (0, 1, ... , n)
        switch (zone_index) {
            case 0:
                zone0.add(s);
                break;
            case 1:
                zone1.add(s);
                break;
            case 2:
                zone2.add(s);
                break;
        }
    }

    public void activateSensorInZone(int zone_index, int sensor_index){
        switch (zone_index) {
            case 0:
                zone0.get(sensor_index).setState(SwitchState.OPEN);
                break;
            case 1:
                zone1.get(sensor_index).setState(SwitchState.OPEN);
                break;
            case 2:
                zone2.get(sensor_index).setState(SwitchState.OPEN);
                break;
        }
    }

    public void deactivateSensorInZone(int zone_index, int sensor_index){
        switch (zone_index) {
            case 0:
                zone0.get(sensor_index).setState(SwitchState.CLOSE);
                break;
            case 1:
                zone1.get(sensor_index).setState(SwitchState.CLOSE);
                break;
            case 2:
                zone2.get(sensor_index).setState(SwitchState.CLOSE);
                break;
        }
    }

    public void checkZone(){
        if(isArmed){
            ArrayList<Integer> openZones = getOpenZones();
            if(openZones.size() != 0){
                siren.play();
                return;
            }
        }
        
        siren.stop();
    }

    // DEBUG
    public void printStates(){
        System.out.println("Zone 0");
        for (int i = 0; i < zone0.size(); i++) {
            System.out.print(" " + zone0.get(i).getState());
        }
        System.out.println();
        System.out.println("Zone 1");
        for (int i = 0; i < zone1.size(); i++) {
            System.out.print(" " + zone1.get(i).getState());
        }
        System.out.println();
    }

    public String getHeader(){
        return "\tCentral";
    }

    public int getState(){
        return isArmed ? 1 : 0;
    }

    public int getSizeZone0(){
        return zone0.size();
    }

    public int getSizeZone1(){
        return zone1.size();
    }

    public int getSizeZone2(){
        return zone2.size();
    }

    public boolean isActive(){
        return isArmed;
    }

    public boolean isNightModeActive(){
        return isNightModeActive;
    }
}
