package SocketMessageSender.CustomServerSender.GetProcesses;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.GetProcesses.GetProcessesServerSide;
import SocketMessageSender.SocketMessageSender;

public class GetProcessesSender extends SocketMessageSender<GetProcessesServerSide> {
    public GetProcessesSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_PROCESSES;
    }
}
