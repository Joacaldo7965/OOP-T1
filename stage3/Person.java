public class Person {
    private float posx, posy;

    private final int id;
    private static int nextId=0;

    public Person(float posx, float posy){
        this.posx = posx;
        this.posy = posy;
    }
    {
        id = nextId++;
    }

    public void moveTo(char direction){
        switch (direction) {
            case '↑': // ascii 8592
                this.posy += 0.5;
                break;
            case '→': // ascii 8593
                this.posx += 0.5;
                break;
            case '↓': // ascii 8594
                this.posy -= 0.5;
                break;
            case '←': // ascii 8595
                this.posx -= 0.5;
                break;
            default:
                System.out.println("Invalid direction");
                break;
        }
    }

    public String getHeader(){
        return "\tpx"+id + "\tpy"+id;
    }

    public String getState(){
        return posx + "\t" + posy;
    }

    public float getPosX(){
        return posx;
    }

    public float getPosY(){
        return posy;
    }
}
