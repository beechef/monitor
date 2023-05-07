package SocketMessageSender.CustomServerSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.LoginUserResultUserSide;
import SocketMessageSender.SocketMessageSender;

public class LoginUserResultUserSideSender extends SocketMessageSender<LoginUserResultUserSide> {
    public LoginUserResultUserSideSender(Sender server) {
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
