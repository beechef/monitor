package SocketMessageReceiver;

import Server.EventDispatcher.SocketMessage;
import Server.EventDispatcher.SocketMessageEvent;
import Server.ServerInstance.Server;

public abstract class SocketMessageReceiver implements SocketMessageEvent {
    public abstract byte getHeadByte();

    public abstract byte getSubHeadByte();

    public abstract void execute(SocketMessage socketMsg);


    protected Server server;

    public SocketMessageReceiver(Server server) {
        this.server = server;
    }

}
