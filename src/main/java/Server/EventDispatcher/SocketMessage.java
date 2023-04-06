package Server.EventDispatcher;

import Server.ServerInstance.Message;

import java.io.IOException;

public class SocketMessage extends SocketMessageGeneric<Message> {
    public SocketMessage(Object sender, byte[] msg) throws IOException, ClassNotFoundException {
        super(sender, msg);
    }
}
