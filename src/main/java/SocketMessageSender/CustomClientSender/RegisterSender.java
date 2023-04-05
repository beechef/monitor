package SocketMessageSender.CustomClientSender;

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
        return EventHeadByte.CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.Connection.REGISTER;
    }
}
