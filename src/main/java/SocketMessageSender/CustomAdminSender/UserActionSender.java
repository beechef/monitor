package SocketMessageSender.CustomAdminSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.UserActionRequestAdminSide;
import SocketMessageSender.SocketMessageSender;

public class UserActionSender extends SocketMessageSender<UserActionRequestAdminSide> {
    public UserActionSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserConnection.ACTION;
    }
}
