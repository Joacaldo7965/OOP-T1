import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Stage2 {
    private ArrayList<Door> doors;
    private ArrayList<Window> windows;
    private Central central;
    private Siren siren;

    public Stage2() {
        doors = new ArrayList<Door>();
        windows = new ArrayList<Window>();
    }

    public void readConfiguration(Scanner in){
        central = new Central();

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
                        System.out.println("Door " + i + " does not exist. Use (0-" + i + ")");
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
                        System.out.println("Window " + i + " does not exist. Use (0-" + i + ")");
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
                            // TODO: ???
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
                // DEBUG
                case 't':
                    central.printStates();
                    break;
                default:
                    System.out.println("Command '" +  command + "' does not exists. Use 'd', 'w', 'k', 'x'");
                    break;
            }
            central.checkZone();
        }
    }

    // DEBUG
    public void printConfiguration(){
        System.out.println(doors.size() + " puerta(s) , " + windows.size() + " ventana(s), 1 Central y 1 Sirena.");
        System.out.println("Doors: " + doors.size());
        System.out.println("Windows: " + windows.size());
        System.out.println("Central: " + central.getSizeZone0() + ", " + central.getSizeZone1());
    }

    public void printHeader(PrintStream out){
        out.print("Step");
        for (int i = 0; i < doors.size(); i++)
            out.print("\t" + doors.get(i).getHeader());
        for (int i = 0; i < windows.size(); i++)
            out.print("\t" + windows.get(i).getHeader());

        // TODO

        out.print("\t" + siren.getHeader());
        out.print("\t" + central.getHeader());
        
        out.println();
    }

    public void printState(int step, PrintStream out){
        out.print(step);
        for(int i = 0; i < doors.size(); i++)
            out.print("\t" + doors.get(i).getState());
        for(int i = 0; i < windows.size(); i++)
            out.print("\t" + windows.get(i).getState());
        // TODO
        out.print("\t" + siren.getState());
        out.print("\t" + central.getState());
        out.println();
    }

    public static void main(String [] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Stage2 <configurationFile.txt>");
            System.exit(-1);
        }
        Scanner in = new Scanner(new File(args[0]));
        //System.out.println("File: " + args[0]);
        Stage2 stage = new Stage2();
        stage.readConfiguration(in);

        // DEBUG
        //stage.printConfiguration();
        
        stage.executeUserInteraction(new Scanner(System.in), new PrintStream(new File("output.csv")));
    }
}
