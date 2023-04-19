public class Frame {
    public State state;

    public Frame(){
        state = State.CLOSE;
    }

    public State getState(){
        return state;
    }

    public void open(){
        state = State.OPEN;
    }

    public void close(){
        state = State.CLOSE;
    }
}
