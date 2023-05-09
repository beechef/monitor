package SocketMessageSender.CustomAdminSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.ChangeForgetPasswordRequest;
import SocketMessageSender.SocketMessageSender;

public class ChangeForgetPasswordSender extends SocketMessageSender<ChangeForgetPasswordRequest> {
    public ChangeForgetPasswordSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.CHANGE_FORGET_PASSWORD;
    }
}
