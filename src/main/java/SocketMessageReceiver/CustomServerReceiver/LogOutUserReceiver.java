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
        var admins = UserController.getAdmins(socketMsg.msg.adminId);
        if (admins.isEmpty()) return;

        var sender = new LogOutUserSender(server);

        for (var admin : admins) {
            var adminSocket = admin.tcpSocket;
            sender.send(adminSocket, socketMsg.msg);
        }

        UserController.removeUser(socketMsg.msg.adminId, socketMsg.msg.deviceId);
    }
}
