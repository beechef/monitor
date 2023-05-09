package SocketMessageReceiver.CustomServerReceiver.ProcessAction;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.CustomServerReceiver.AdminUserReceiver;
import SocketMessageReceiver.DataType.ProcessAction.ProcessActionResultUserSide;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomUserSender.ProcessActionResultSender;

import java.util.Objects;

public class ProcessActionResultReceiver extends SocketMessageReceiver<ProcessActionResultUserSide> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.PROCESS_ACTION_RESULT;
    }


    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<ProcessActionResultUserSide> socketMsg) {
        var admin = UserController.getAdmin(socketMsg.msg.adminId, socketMsg.msg.adminUuid);
        if (admin == null) return;

        var adminSocket = admin.tcpSocket;
        var sender = new ProcessActionResultSender(server);
        sender.send(adminSocket, socketMsg.msg);
    }
}
