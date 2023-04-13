package SocketMessageSender.CustomUserSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.LoginUserRequest;
import SocketMessageSender.SocketMessageSender;

public class LoginSender extends SocketMessageSender<LoginUserRequest> {

    public LoginSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserConnection.LOGIN;
    }
}

