package SocketMessageReceiver.CustomServerReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.UserActionResultServerSide;
import SocketMessageReceiver.DataType.UserActionResultUserSide;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.UserActionResultSender;

public class UserActionResultReceiver extends SocketMessageReceiver<UserActionResultUserSide> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserConnection.ACTION_RESULT;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<UserActionResultUserSide> socketMsg) {
        var admin = UserController.getAdmin(socketMsg.msg.adminId, socketMsg.msg.adminUuid);
        if (admin == null) return;

        var sender = new UserActionResultSender(server);
        var result = new UserActionResultServerSide(socketMsg.msg.action, socketMsg.msg.result, socketMsg.msg.message);
        sender.send(admin.tcpSocket, result);
    }
}
