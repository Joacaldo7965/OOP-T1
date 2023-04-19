import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Stage1 {
    private ArrayList<Door> doors;
    private ArrayList<Window> windows;


    public Stage1() {
        doors = new ArrayList<Door>();
        windows = new ArrayList<Window>();
    }

    public void readConfiguration(Scanner in){
        // reading <#_doors> <#_windows> <#_PIRs>
        int numDoors = in.nextInt();
        for (int i = 0; i < numDoors; i++)
            doors.add(new Door());
        int numWindows = in.nextInt();
        for (int i = 0; i < numWindows; i++)
            windows.add(new Window());
        
        // TODO
        in.close();
    }

    public void executeUserInteraction (Scanner in, PrintStream out){
        char command, parameter;
        String strIndex;
        int index;

        int step = 0;
        boolean done = false;
        printHeader(out);
        while (!done) {
            printState(step++, out);

            // DEBUG
            System.out.print("Step " + step + ": ");

            String input = in.next();
            command = input.charAt(0);
            switch (command) {
                case 'd':
                    strIndex = input.substring(1, input.length());

                    // Assuming correct input
                    index = Integer.parseInt(strIndex);  //input.charAt(1); -> dont get numbers with more than 1 digit
                    
                    parameter = in.next().charAt(0);

                    // DEBUG
                    // System.out.println("command: '" + command + "'");
                    // System.out.println("index: '" + index + "'");
                    // System.out.println("parameter: '" + parameter + "'");

                    // Index validation
                    if(index >= doors.size()){
                        System.out.println("Door " + index + " does not exist. (Use 0-" + index + ")");
                        break;
                    }

                    if (parameter == 'o')
                        doors.get(index).open();
                    else if(parameter == 'c')
                        doors.get(index).close();
                    else
                        System.out.println("Parameter '" + parameter + "' does not exist. (Use 'o' or 'c')");
                    break;
                case 'w':
                    strIndex = input.substring(1, input.length());

                    // Assuming correct input
                    index = Integer.parseInt(strIndex);  //input.charAt(1); -> dont get numbers with more than 1 digit
                    
                    parameter = in.next().charAt(0);

                    // Index validation
                    if(index >= windows.size()){
                        System.out.println("Window " + index + " does not exist. (Use 0-" + index + ")");
                        break;
                    }

                    if (parameter == 'o')
                        windows.get(index).open();
                    else if(parameter == 'c')
                        windows.get(index).close();
                    else
                        System.out.println("Parameter '" + parameter + "' does not exist. (Use 'o' or 'c')");
                    break;
                case 'x': 
                    done=true;   // Added to finish the program
                    break;
            }
        }
    }

    // DEBUG
    public void printConfiguration(){
        System.out.println(doors.size() + " puerta(s) y " + windows.size() + " ventana(s).");
    }

    public void printHeader(PrintStream out){
        out.print("Step");
        for (int i = 0; i < doors.size(); i++)
            out.print("\t" + doors.get(i).getHeader());
        for (int i = 0; i < windows.size(); i++)
            out.print("\t" + windows.get(i).getHeader());
        
        // TODO
        out.println();
    }

    public void printState(int step, PrintStream out){
        out.print(step);
        for(int i = 0; i < doors.size(); i++)
            out.print("\t" + doors.get(i).getState());
        for(int i = 0; i < windows.size(); i++)
            out.print("\t" + windows.get(i).getState());
        
        // TODO
        out.println();
    }

    public static void main(String [] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Stage1 <configurationFile.txt>");
            System.exit(-1);
        }
        Scanner in = new Scanner(new File(args[0]));
        System.out.println("File: " + args[0]);
        Stage1 stage = new Stage1();
        stage.readConfiguration(in);
        stage.printConfiguration();
        stage.executeUserInteraction(new Scanner(System.in), new PrintStream(new File("output.csv")));
    }
}
