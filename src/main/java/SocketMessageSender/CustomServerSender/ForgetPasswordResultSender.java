package SocketMessageSender.CustomServerSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.ForgetPasswordResult;
import SocketMessageSender.SocketMessageSender;

public class ForgetPasswordResultSender extends SocketMessageSender<ForgetPasswordResult> {
    public ForgetPasswordResultSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.FORGET_PASSWORD_RESULT;
    }
}
