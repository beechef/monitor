package SocketMessageSender.CustomUserSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.KeyLogRequest;
import SocketMessageSender.SocketMessageSender;

public class KeyLogSender extends SocketMessageSender<KeyLogRequest> {
    public KeyLogSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.SEND_KEY_LOG;
    }
}
