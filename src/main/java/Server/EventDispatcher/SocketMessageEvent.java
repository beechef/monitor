package Server.EventDispatcher;

import Server.ServerInstance.Sender;

public interface SocketMessageEvent {
    byte getHeadByte();

    byte getSubHeadByte();

    void execute(Sender server, SocketMessage data);
}
