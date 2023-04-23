package SocketMessageSender.CustomServerSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.ChangeUserNameResult;
import SocketMessageSender.SocketMessageSender;

public class ChangeUserNameResultSender extends SocketMessageSender<ChangeUserNameResult> {
    public ChangeUserNameResultSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.CHANGE_NAME_RESULT;
    }
}
