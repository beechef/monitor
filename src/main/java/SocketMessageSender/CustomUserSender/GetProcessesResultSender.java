package SocketMessageSender.CustomUserSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.GetProcessesResult.GetProcessesResultUserSide;
import SocketMessageSender.SocketMessageSender;

public class GetProcessesResultSender extends SocketMessageSender<GetProcessesResultUserSide> {
    public GetProcessesResultSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_PROCESSES_RESULT;
    }
}
