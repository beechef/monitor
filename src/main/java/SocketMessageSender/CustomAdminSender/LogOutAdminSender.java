package SocketMessageSender.CustomAdminSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.LogOutAdminRequest;
import SocketMessageSender.SocketMessageSender;

public class LogOutAdminSender extends SocketMessageSender<LogOutAdminRequest> {
    public LogOutAdminSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.LOGOUT;
    }
}
