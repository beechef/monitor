package SocketMessageSender;

import Server.ServerInstance.Message;
import Server.ServerInstance.Sender;

public abstract class SocketMessageSender<T> {
    public abstract byte getHeadByte();

    public abstract byte getSubHeadByte();

    private final Sender server;

    public SocketMessageSender(Sender server) {
        this.server = server;
    }

    public void send(Object target, T data) {
        server.send(target, new Message(getHeadByte(), getSubHeadByte(), data));
    }
}
