package SocketMessageSender.CustomAdminSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.ChangeUserNameRequest;
import SocketMessageSender.SocketMessageSender;

public class ChangeUserNameSender extends SocketMessageSender<ChangeUserNameRequest> {
    public ChangeUserNameSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.CHANGE_NAME;
    }
}
