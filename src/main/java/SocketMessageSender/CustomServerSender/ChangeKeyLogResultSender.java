package SocketMessageSender.CustomServerSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.ChangeKeyLogConfigResult;
import SocketMessageSender.SocketMessageSender;

public class ChangeKeyLogResultSender extends SocketMessageSender<ChangeKeyLogConfigResult> {
    public ChangeKeyLogResultSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.CHANGE_KEY_LOG_CONFIG_RESULT;
    }
}
