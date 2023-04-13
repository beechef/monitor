package SocketMessageSender.CustomServerSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.RegisterResultRequest;
import SocketMessageSender.SocketMessageSender;

public class RegisterResultSender extends SocketMessageSender<RegisterResultRequest> {
    public RegisterResultSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.REGISTER_RESULT;
    }
}
