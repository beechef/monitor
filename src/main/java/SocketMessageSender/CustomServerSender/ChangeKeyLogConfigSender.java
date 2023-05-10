package SocketMessageSender.CustomServerSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.ChangeKeyLogConfigRequest;
import SocketMessageSender.SocketMessageSender;

public class ChangeKeyLogConfigSender extends SocketMessageSender<ChangeKeyLogConfigRequest> {
    public ChangeKeyLogConfigSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.CHANGE_KEY_LOG_CONFIG;
    }
}
