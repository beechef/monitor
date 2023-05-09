package SocketMessageSender.CustomServerSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.ChangeForgetPasswordResult;
import SocketMessageSender.SocketMessageSender;

public class ChangeForgetPasswordResultSender extends SocketMessageSender<ChangeForgetPasswordResult> {
    public ChangeForgetPasswordResultSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.CHANGE_FORGET_PASSWORD_RESULT;
    }
}
