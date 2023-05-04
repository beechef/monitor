package SocketMessageSender.CustomServerSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.ProcessAction.ProcessActionRequestServerSide;
import SocketMessageSender.SocketMessageSender;

public class ProcessActionSender extends SocketMessageSender<ProcessActionRequestServerSide> {
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
