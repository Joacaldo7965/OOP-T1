import java.io.File;
import java.net.URL;

public class Siren {
    private URL dir;
    private boolean isSounding;
    private AePlayWave aWave;

    public Siren (String soundFileName){
        try {
            dir = new File(soundFileName).toURI().toURL();
        }
        catch (Exception exc){
            exc.printStackTrace(System.out);
        }
        isSounding = false;
    }
    public void play(){
        // TODO ...
        aWave = new AePlayWave(dir);
        aWave.start();
    }
    public void stop(){
        // TODO ...
        aWave.stopSounding();
    }
    public String getHeader() {
        return "Siren";
    }
    public int getState() {
        return (isSounding ? 1 : 0); // TODO: Check if it's reversed
    }
    
}
