package SocketMessageSender.CustomAdminSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.LoginAdminUdpRequest;
import SocketMessageSender.SocketMessageSender;

public class LoginAdminUdpSender extends SocketMessageSender<LoginAdminUdpRequest> {
    public LoginAdminUdpSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.LOGIN_UDP;
    }
}
