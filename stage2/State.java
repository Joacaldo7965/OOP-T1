public enum State {
    CLOSE,
    OPEN,
    ;

    @Override
    public String toString(){
      switch (this) {
        case CLOSE:
            return "1";
        case OPEN:
            return "0";
        default:
            return "";
      }
    }
}
