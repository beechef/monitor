package SocketMessageSender.CustomUserSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.LoginUserUDPRequest;
import SocketMessageSender.SocketMessageSender;

public class LoginUDPSender extends SocketMessageSender<LoginUserUDPRequest> {
    public LoginUDPSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserConnection.LOGIN_UDP;
    }
}
