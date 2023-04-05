package SocketMessageReceiver;

import Server.EventDispatcher.SocketMessage;
import Server.EventDispatcher.SocketMessageEvent;
import Server.ServerInstance.Sender;

public abstract class SocketMessageReceiver implements SocketMessageEvent {
    public abstract byte getHeadByte();

    public abstract byte getSubHeadByte();

    public abstract void execute(SocketMessage socketMsg);


    protected Sender server;

    public SocketMessageReceiver(Sender server) {
        this.server = server;
    }

}
