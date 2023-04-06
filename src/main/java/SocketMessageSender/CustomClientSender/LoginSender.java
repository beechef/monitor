package SocketMessageSender.CustomClientSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.LoginRequest;
import SocketMessageSender.SocketMessageSender;

public class LoginSender extends SocketMessageSender<LoginRequest> {
    public LoginSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.Connection.LOGIN;
    }
}
