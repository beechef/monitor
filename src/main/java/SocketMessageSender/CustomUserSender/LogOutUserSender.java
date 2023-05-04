package SocketMessageSender.CustomUserSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.LogOutUserRequest;
import SocketMessageSender.SocketMessageSender;

public class LogOutUserSender extends SocketMessageSender<LogOutUserRequest> {
    public LogOutUserSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserConnection.LOGOUT;
    }
}
