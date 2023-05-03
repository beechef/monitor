package SocketMessageSender.CustomAdminSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.ProcessAction.ProcessActionRequestAdminSide;
import SocketMessageSender.SocketMessageSender;

public class ProcessActionSender extends SocketMessageSender<ProcessActionRequestAdminSide> {
    public ProcessActionSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.PROCESS_ACTION;
    }
}
