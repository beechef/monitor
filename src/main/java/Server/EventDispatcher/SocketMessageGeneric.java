package Server.EventDispatcher;

import Utilities.Utilities;

import java.io.IOException;

public class SocketMessageGeneric<T> {
    public Object sender;
    public T msg;

    public SocketMessageGeneric(Object sender, byte[] msg) throws IOException, ClassNotFoundException {
        this.sender = sender;
        this.msg = castMessage(msg);
    }

    public SocketMessageGeneric(Object sender, T msg) {
        this.sender = sender;
        this.msg = msg;
    }

    private T castMessage(byte[] bytes) throws IOException, ClassNotFoundException {
        return Utilities.castBytes(bytes);
    }
}
