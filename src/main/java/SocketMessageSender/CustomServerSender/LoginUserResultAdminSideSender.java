package SocketMessageSender.CustomServerSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.LoginUserResultAdminSide;
import SocketMessageSender.SocketMessageSender;

public class LoginUserResultAdminSideSender extends SocketMessageSender<LoginUserResultAdminSide> {
    public LoginUserResultAdminSideSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserConnection.LOGIN_RESULT;
    }
}
