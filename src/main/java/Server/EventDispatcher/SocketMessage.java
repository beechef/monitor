package Server.EventDispatcher;

import Server.ServerInstance.Message;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;

public class SocketMessage extends SocketTCPMessageGeneric<Message> {
    public SocketMessage(AsynchronousSocketChannel sender, byte[] msg) throws IOException, ClassNotFoundException {
        super(sender, msg);
    }
}
