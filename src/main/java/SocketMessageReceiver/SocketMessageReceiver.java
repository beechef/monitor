package SocketMessageReceiver;

import Server.EventDispatcher.SocketMessage;
import Server.EventDispatcher.SocketMessageEvent;

public abstract class SocketMessageReceiver implements SocketMessageEvent {
    public abstract byte getHeadByte();
    public abstract byte getSubHeadByte();

    public abstract void execute(SocketMessage socketMsg);

}
