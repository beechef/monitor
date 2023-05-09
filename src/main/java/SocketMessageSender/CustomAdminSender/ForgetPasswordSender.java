package SocketMessageSender.CustomAdminSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.ForgetPasswordRequest;
import SocketMessageSender.SocketMessageSender;

public class ForgetPasswordSender extends SocketMessageSender<ForgetPasswordRequest> {
    public ForgetPasswordSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.FORGET_PASSWORD;
    }
}
