package SocketMessageSender;

import Server.ServerInstance.Message;
import Server.ServerInstance.Sender;

public abstract class SocketMessageSender<T> {
    public abstract byte getHeadByte();

    public abstract byte getSubHeadByte();

    private final Sender sender;

    public SocketMessageSender(Sender sender) {
        this.sender = sender;
    }

    public void send(Object target, T data) {
        sender.send(target, new Message(getHeadByte(), getSubHeadByte(), data));
    }
}
