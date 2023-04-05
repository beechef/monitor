package Server.EventDispatcher.EventHead;

public class EventHeadByte {

    public static final byte CONNECTION = 1;

    public static class Connection {
        public static final byte LOGIN = 1;
        public static final byte LOGOUT = 2;
        public static final byte REGISTER = 3;

        public static final byte REGISTER_RESULT = 4;
    }


}
