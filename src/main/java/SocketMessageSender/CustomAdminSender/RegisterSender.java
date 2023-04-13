package SocketMessageSender.CustomAdminSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.RegisterRequest;
import SocketMessageSender.SocketMessageSender;

public class RegisterSender extends SocketMessageSender<RegisterRequest> {
    public RegisterSender(Sender sender) {
        super(sender);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.REGISTER;
    }
}
