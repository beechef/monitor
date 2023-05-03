package Server.EventDispatcher.EventHead;

public class EventHeadByte {

    public static final byte ADMIN_CONNECTION = 1;

    public static class AdminConnection {
        public static final byte LOGIN = 1;
        public static final byte LOGIN_RESULT = 2;

        public static final byte REGISTER = 3;
        public static final byte REGISTER_RESULT = 4;
    }

    public static final byte USER_CONNECTION = 2;

    public static class UserConnection {
        public static final byte LOGIN = 1;
        public static final byte LOGIN_RESULT = 2;

        public static final byte LOGIN_UDP = 3;
    }

    public static final byte USER_DATA = 3;

    public static class UserData {
        public static final byte GET = 1;
        public static final byte GET_RESULT = 2;

        public static final byte GET_HARDWARE_INFO = 3;
        public static final byte GET_HARDWARE_INFO_RESULT = 4;

        public static final byte GET_PROCESSES = 5;
        public static final byte GET_PROCESSES_RESULT = 6;

        public static final byte CHANGE_NAME = 7;
        public static final byte CHANGE_NAME_RESULT = 8;

        public static final byte PROCESS_ACTION = 9;
        public static final byte PROCESS_ACTION_RESULT = 10;

        public static final byte GET_IMAGE = 11;
        public static final byte GET_IMAGE_RESULT = 12;
    }

}
