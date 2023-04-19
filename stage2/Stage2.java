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
        central = new Central(3); // n_zones = 3 by definition

        // reading <#_doors> <#_windows> <#_PIRs>
        int numDoors = in.nextInt();
        for (int i = 0; i < numDoors; i++) {
            Door d = new Door();
            doors.add(d); // Needed??

            if(i == 0) // First door added to Zone 0
                central.addNewSensor(0, d);
            else // Other doors added to Zone 1
                central.addNewSensor(1, d);
        }
        int numWindows = in.nextInt();
        for (int i = 0; i < numWindows; i++) {
            Window w = new Window();
            windows.add(w);

            // All windows added to Zone 1
            central.addNewSensor(1, w);
        }
        in.nextLine(); // ???

        String soundFile = in.next();
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

                    if (parameter== 'o'){
                        //doors.get(i).open();
                        if(i == 0) // Open door 0 from zone 0
                            central.openFrameInZone(0, i);
                        else // All other doors are in zone 1
                            central.openFrameInZone(1, i);
                        
                    } else if(parameter == 'c'){
                        //doors.get(i).close();
                        if(i == 0) // Close door 0 from zone 0
                            central.closeFrameInZone(0, i);
                        else // All other doors are in zone 1
                            central.closeFrameInZone(1, i);
                        
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

                    if (parameter == 'o')
                        //windows.get(i).open();
                        central.openFrameInZone(1, i); // All windows are from zone 1
                    else if(parameter == 'c')
                        //windows.get(i).close();
                        central.closeFrameInZone(1, i); // All windows are from zone 1
                    else
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
                default:
                    System.out.println("Command '" +  command + "' does not exists. Use 'd', 'w', 'k', 'x'");
                    break;
            }
            central.checkZone();
        }
    }

    // DEBUG
    public void printConfiguration(){
        System.out.println(doors.size() + " puerta(s) y " + windows.size() + " ventana(s).");
    }

    public void printHeader(PrintStream out){
        out.print("Step");
        for (int i = 0; i < doors.size(); i++){
            central.
        }
        for (int i = 0; i < doors.size(); i++)
            out.print("\t" + doors.get(i).getHeader());
        for (int i = 0; i < windows.size(); i++)
            out.print("\t" + windows.get(i).getHeader());
        
        // TODO
        out.println();
    }

    public void printState(int step, PrintStream out){
        ...
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
        stage.executeUserInteraction(new Scanner(System.in), new PrintStream(new File("output.csv")));
    }
}
