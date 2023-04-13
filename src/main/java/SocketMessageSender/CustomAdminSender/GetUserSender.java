package SocketMessageSender.CustomAdminSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.GetUserRequest;
import SocketMessageSender.SocketMessageSender;

public class GetUserSender extends SocketMessageSender<GetUserRequest> {

    public GetUserSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET;
    }
}

