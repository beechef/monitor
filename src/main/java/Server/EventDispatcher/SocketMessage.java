package Server.EventDispatcher;

import Server.ServerInstance.Message;
import Utilities.Utilities;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;

public class SocketMessage {
    public AsynchronousSocketChannel sender;
    public Message msg;

    public SocketMessage(AsynchronousSocketChannel sender, byte[] msg) throws IOException, ClassNotFoundException {
        this.sender = sender;
        this.msg = castMessage(msg);
    }

    private Message castMessage(byte[] bytes) throws IOException, ClassNotFoundException {
        return Utilities.castBytes(bytes);
    }
}
