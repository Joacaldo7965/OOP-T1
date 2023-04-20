public class Person {
    float posx, posy;

    public Person(float posx, float posy){
        this.posx = posx;
        this.posy = posy;
    }

    public moveTo(String direction){
        switch (direction) {
            case "↑":
                break;
            case "→":
                break;
            case "↓":
                break;
            case "←":
                break;
            default:
                System.out.println("Invalid direction");
                break;
        }
    }
}
