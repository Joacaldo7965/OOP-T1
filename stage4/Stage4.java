import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Stage4 {
    private ArrayList<Door> doors;
    private ArrayList<Window> windows;
    private ArrayList<PIR_Detector> pirs;
    private ArrayList<Person> people;
    private Central central;
    private Siren siren;
    
    public Stage4(){
        doors = new ArrayList<Door>();
        windows = new ArrayList<Window>();
        pirs = new ArrayList<PIR_Detector>();
        people = new ArrayList<Person>();
        central = new Central();
    }

    public void readConfiguration(Scanner in){

        // reading <#_doors> <#_windows> <#_PIRs>
        int numDoors = in.nextInt();
        for (int i = 0; i < numDoors; i++) {
            Door d = new Door();
            doors.add(d);

            if(i == 0) // First door added to Zone 0
                central.addNewSensor(0, d.getSensor());
            else // Other doors added to Zone 1
                central.addNewSensor(1, d.getSensor());
        }

        int numWindows = in.nextInt();
        for (int i = 0; i < numWindows; i++) {
            Window w = new Window();
            windows.add(w);

            // All windows added to Zone 1
            central.addNewSensor(1, w.getSensor());
        }

        int numPIRs = in.nextInt();
        for (int i = 0; i < numPIRs; i++) {
            float x = in.nextFloat();
            float y = in.nextFloat();
            float dir_angle = (float) in.nextInt();
            float sens_angle = (float) in.nextInt();
            float sens_range = (float) in.nextInt();

            PIR_Detector p = new PIR_Detector(x, y, dir_angle, sens_angle, sens_range);

            pirs.add(p);

            // All PIRs added to Zone 2
            central.addNewSensor(2, p);
        }

        in.nextLine(); // ???

        String soundFile = in.next();

        // DEBUG
        //System.out.println("Sound file: '" + soundFile + "'");

        siren = new Siren(soundFile);
        central.setSiren(siren);

        in.close();
    }

    public void executeUserInteraction (Scanner in, PrintStream out){
        String command;
        char parameter;
        int i;

        int step=0;

        printHeader(out);
        while (true) {
            printState(step++, out);

            // DEBUG
            System.out.print("Step " + step + ": ");

            command = in.next();
            if (command.charAt(0)=='x') break;
            
            switch (command.charAt(0)) {
                case 'd':
                    i = Integer.parseInt(command.substring(1));
                    parameter = in.next().charAt(0);

                    // Index validation
                    if(i >= doors.size()){
                        System.out.println("Door " + i + " does not exist. Use (0-" + (doors.size()-1) + ")");
                        break;
                    }

                    if (parameter == 'o'){
                        doors.get(i).open();

                        if(i == 0){
                            central.activateSensorInZone(0, i);
                        } else{
                            central.activateSensorInZone(1, i - 1);
                        }  
                    } else if(parameter == 'c'){
                        doors.get(i).close();
                        if(i == 0) // Close door 0 from zone 0
                            central.deactivateSensorInZone(0, i);
                        else // All other doors are in zone 1
                            central.deactivateSensorInZone(1, i - 1); 
                    } else
                        System.out.println("Parameter '" + parameter + "' does not exist. Use 'o' or 'c'");
                    break;
                case 'w':
                    i = Integer.parseInt(command.substring(1));
                    parameter = in.next().charAt(0);

                    // Index validation
                    if(i >= windows.size()){
                        System.out.println("Window " + i + " does not exist. Use (0-" + (windows.size()-1) + ")");
                        break;
                    }

                    if (parameter == 'o'){
                        windows.get(i).open();
                        central.activateSensorInZone(1, doors.size() + i - 1); // Offset of n doors - 1 door in zone 0
                    } else if(parameter == 'c'){
                        windows.get(i).close();
                        central.deactivateSensorInZone(1, doors.size() + i - 1); // Offset of n doors - 1 door in zone 0
                    } else
                        System.out.println("Parameter '" + parameter + "' does not exist. Use 'o' or 'c'");                 
                    break;
                case 'k':
                    parameter = in.next().charAt(0);
                    switch (parameter) {
                        case 'a':
                            // Arm all
                            central.arm();
                            break;
                        case 'p': 
                            // Arm perimeter
                            central.armNightMode();
                            break;
                        case 'd':
                            // Disarm
                            central.disarm();
                            break;
                        default:
                            System.out.println("Parameter '" + parameter + "' does not exists. Use 'a', 'p', 'd'");
                            break;
                    }
                    break;
                case 'c':
                    float inputX = in.nextFloat();
                    float inputY = in.nextFloat();

                    people.add(new Person(inputX, inputY));

                    break;
                case 'p':
                    i = Integer.parseInt(command.substring(1));
                    parameter = in.next().charAt(0);

                    // Index validation
                    if(i >= people.size()){
                        System.out.println("Person " + i + " does not exist. Use (0-" + (people.size()-1) + ")");
                        break;
                    }

                    people.get(i).moveTo(parameter);

                    break;
                // DEBUG
                case 't':
                    central.printStates();
                    break;
                default:
                    System.out.println("Command '" +  command + "' does not exists. Use 'd', 'w', 'k', 'x'");
                    break;
            }
            
            if(central.isActive() && !central.isNightModeActive()){
                // For each PIR and for each person check for detection
                for (int j = 0; j < pirs.size(); j++) {
                    boolean founded = pirs.get(j).detectPeople(people);
                    //System.out.println("founded: " + founded);
                    if (!founded) // Found nobody
                        central.deactivateSensorInZone(2, j);
                    else // Found at least 1 person
                        central.activateSensorInZone(2, j);     
                }
            }

            central.checkZone();
        }
    }

    // DEBUG
    public void printConfiguration(){
        System.out.println(doors.size() + " door(s) , " + windows.size() + " window(s), " + pirs.size() + " PIR(s), 1 Central y 1 Siren.");
        System.out.println("Central zones: " + central.getSizeZone0() + ", " + central.getSizeZone1() + ", " + central.getSizeZone2());
    }

    public void printHeader(PrintStream out){
        out.print("Step");
        for (int i = 0; i < doors.size(); i++)
            out.print(doors.get(i).getHeader());
        for (int i = 0; i < windows.size(); i++)
            out.print(windows.get(i).getHeader());
        for (int i = 0; i < pirs.size(); i++)
            out.print(pirs.get(i).getHeader());

        out.print(siren.getHeader());
        out.print(central.getHeader());

        for (int i = 0; i < people.size(); i++)
            out.print(people.get(i).getHeader());
        
        out.println();
    }

    public void printState(int step, PrintStream out){
        out.print(step);
        for(int i = 0; i < doors.size(); i++)
            out.print("\t" + doors.get(i).getState());
        for(int i = 0; i < windows.size(); i++)
            out.print("\t" + windows.get(i).getState());
        for (int i = 0; i < pirs.size(); i++)
            out.print("\t" + pirs.get(i).getState());

        out.print("\t" + siren.getState());
        out.print("\t" + central.getState());
        for (int i = 0; i < people.size(); i++) {
            out.print("\t" + people.get(i).getState());
        }
        out.println();
    }

    public static void main(String [] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Stage4 <configurationFile.txt>");
            System.exit(-1);
        }
        Scanner in = new Scanner(new File(args[0]));
        //System.out.println("File: " + args[0]);
        Stage4 stage = new Stage4();
        stage.readConfiguration(in);

        // DEBUG
        stage.printConfiguration();
        
        stage.executeUserInteraction(new Scanner(System.in), new PrintStream(new File("output.csv")));
    }
}
