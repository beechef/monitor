package SocketMessageSender.CustomServerSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.GetLogResult;
import SocketMessageSender.SocketMessageSender;

public class GetLogResultSender extends SocketMessageSender<GetLogResult> {
    public GetLogResultSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_LOG_RESULT;
    }
}
