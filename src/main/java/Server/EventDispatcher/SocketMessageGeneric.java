package Server.EventDispatcher;

import Utilities.Utilities;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;

public class SocketMessageGeneric<T> {
    public AsynchronousSocketChannel sender;
    public T msg;

    public SocketMessageGeneric(AsynchronousSocketChannel sender, byte[] msg) throws IOException, ClassNotFoundException {
        this.sender = sender;
        this.msg = castMessage(msg);
    }

    public SocketMessageGeneric(AsynchronousSocketChannel sender, T msg) {
        this.sender = sender;
        this.msg = msg;
    }

    private T castMessage(byte[] bytes) throws IOException, ClassNotFoundException {
        return Utilities.castBytes(bytes);
    }
}
