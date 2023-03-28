package Server.EventDispatcher.EventHead;

public class EventHeadByte {

    public static final byte CONNECTION = 1;

    public static class Connection {
        public static final byte LOGIN = 1;
        public static final byte REGISTER = 2;
    }
}
