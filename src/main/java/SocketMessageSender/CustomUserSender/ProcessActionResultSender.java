package SocketMessageSender.CustomUserSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.ProcessAction.ProcessActionResultUserSide;
import SocketMessageSender.SocketMessageSender;

public class ProcessActionResultSender extends SocketMessageSender<ProcessActionResultUserSide> {
    public ProcessActionResultSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.PROCESS_ACTION_RESULT;
    }
}
