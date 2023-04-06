package SocketMessageSender.CustomServerSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.LoginResultRequest;
import SocketMessageSender.SocketMessageSender;

public class LoginResultSender extends SocketMessageSender<LoginResultRequest> {
    public LoginResultSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.Connection.LOGIN_RESULT;
    }
}
