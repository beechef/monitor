package SocketMessageReceiver.CustomServerReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.LogOutUserRequest;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomUserSender.LogOutUserSender;


public class LogOutUserReceiver extends SocketMessageReceiver<LogOutUserRequest> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserConnection.LOGOUT;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<LogOutUserRequest> socketMsg) {
        var admin = UserController.getAdmin(socketMsg.msg.adminId);
        if (admin == null) return;

        var adminSocket = admin.tcpSocket;
        var sender = new LogOutUserSender(server);
        sender.send(adminSocket, socketMsg.msg);

        UserController.removeUser(admin.adminId, socketMsg.msg.deviceId);
    }
}
