package SocketMessageSender.CustomServerSender.GetProcesses;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.GetProcessesResult.GetProcessesResultServerSide;
import SocketMessageSender.SocketMessageSender;

public class GetProcessesResultSender extends SocketMessageSender<GetProcessesResultServerSide> {
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
