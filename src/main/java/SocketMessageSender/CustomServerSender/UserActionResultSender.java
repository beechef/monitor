package SocketMessageSender.CustomServerSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.UserActionResultServerSide;
import SocketMessageSender.SocketMessageSender;

public class UserActionResultSender extends SocketMessageSender<UserActionResultServerSide> {
    public UserActionResultSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserConnection.ACTION_RESULT;
    }
}
