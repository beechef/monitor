package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class ProcessInfo implements Serializable {
    private static final int INT_SIZE = 4;
    private static final int UNICODE_CHAR_SIZE = 2;

    public int id;

    public String name;
    public String path;

    public ProcessInfo(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public int getSize() {
        return INT_SIZE + name.length() * UNICODE_CHAR_SIZE + path.length() * UNICODE_CHAR_SIZE;
    }
}
