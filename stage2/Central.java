import java.util.ArrayList;

public class Central {

    // TODO: create class Zone ??
    private ArrayList<ArrayList<Frame>> zones;
    //private ArrayList<Frame> zone0;
    //private ArrayList<Frame> zone1;

    private boolean isArmed;
    private Siren siren;

    public Central(int n_zones){
        zones = new ArrayList<ArrayList<Frame>>(n_zones);

        for(int i = 0; i < n_zones; i++){
            zones.add(new ArrayList<Frame>());
        }

        isArmed = false;
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

    public ArrayList<Integer> getOpenZones(){
        ArrayList<Integer> zonesOpen = new ArrayList<Integer>();

        // For each zone z
        for(int z = 0; z < zones.size(); z++){
            ArrayList<Frame> zoneZ = zones.get(z);
            // Loop through the zone until open frame
            for(int i = 0; i < zoneZ.size(); i++)
                if(zoneZ.get(i).getState() == State.OPEN){
                    zonesOpen.add(z);
                    break;
                }
        }
                
        return zonesOpen;
    }

    public void disarm() {
        // TODO: ??
        isArmed = false;
    }

    public void setSiren(Siren s) {
        siren = s;
    }

    public void addNewSensor(int zone_index, Frame s){
        // zone_index : (0, 1, ... , n)
        if(s.getClass() == Door.class){
            Door d = (Door) s;
            zones.get(zone_index).add(d);   
        } else if(s.getClass() == Window.class){
            Window w = (Window) s;
            zones.get(zone_index).add(w);
        } else{
            System.out.println("EROOOOOOOOOOOOOOOOR");
        }

        // ArrayList<Frame> zoneSelected = zones.get(zone_index);
        // zoneSelected.add(s);
        // zones.set(zone_index, zoneSelected);
        zones.get(zone_index).add(s);
    }

    // public void addNewDoor(int zone_index, Door s){
    //     // zone_index : (0, 1, ... , n)

    //     // ArrayList<Frame> zoneSelected = zones.get(zone_index);
    //     // zoneSelected.add(s);
    //     // zones.set(zone_index, zoneSelected);
    //     zones.get(zone_index).add(s);
    // }

    // public void addNewWindow(int zone_index, Window s){ 
    //     // zone_index : (0, 1, ... , n)

    //     // ArrayList<Frame> zoneSelected = zones.get(zone_index);
    //     // zoneSelected.add(s);
    //     // zones.set(zone_index, zoneSelected);
    //     zones.get(zone_index).add(s);
    // }

    public void openFrameInZone(int zone_index, int frame_index){
        //ArrayList<Frame> zoneSelected = zones.get(zone_index);

        //zoneSelected.get(frame_index)

        //zones.set(zone_index, zoneSelected);
        zones.get(zone_index).get(frame_index).open(); // With this im opening the Frame but not the magnetic sensor Oops
    }


    public void closeFrameInZone(int zone_index, int frame_index){
        zones.get(zone_index).get(frame_index).close(); // With this im opening the Frame but not the magnetic sensor Oops

    }

    public void checkZone(){
        // TODO
        if(isArmed){
            ArrayList<Integer> openZones = getOpenZones();
            if(openZones.size() != 0)
                siren.play();
            else{
                //siren.stop(); // ??? We dont want intruders to simply close the door to shut the alarm xD
            }

        }
        siren.stop();
    }

    public String getHeader(){
        
        for(int z = 0; z < zones.size(); z++){
            ArrayList<Frame> zoneZ = zones.get(z);
            // Loop through the zone until open frame
            for(int i = 0; i < zoneZ.size(); i++){
                Frame f = zoneZ.get(i);
                if(f.getClass() == Door.class){
                    Door d = (Door) f;
                    d.getHeader()
                } else if(f.getClass() == Window.class){
                    Window w = (Window) f;
                }
            }
                
        }
        return "Central";
    }

    public String getZoneHeader(){

        
        return "";
    }

    public int getState(){
        return isArmed ? 1 : 0;
    }
}
