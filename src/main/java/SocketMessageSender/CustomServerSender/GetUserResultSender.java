package SocketMessageSender.CustomServerSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.GetUserResultRequest;
import SocketMessageSender.SocketMessageSender;

public class GetUserResultSender extends SocketMessageSender<GetUserResultRequest> {
    public GetUserResultSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_RESULT;
    }
}
