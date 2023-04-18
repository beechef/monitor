package SocketMessageSender.CustomAdminSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.GetProcesses.GetProcessesAdminSide;
import SocketMessageSender.SocketMessageSender;

public class GetProcessesSender extends SocketMessageSender<GetProcessesAdminSide> {
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
