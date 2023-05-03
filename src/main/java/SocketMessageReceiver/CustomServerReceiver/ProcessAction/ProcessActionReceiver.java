package SocketMessageReceiver.CustomServerReceiver.ProcessAction;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.CustomServerReceiver.AdminUserReceiver;
import SocketMessageReceiver.DataType.ProcessAction.ProcessActionRequestAdminSide;
import SocketMessageReceiver.DataType.ProcessAction.ProcessActionRequestServerSide;
import SocketMessageSender.CustomServerSender.ProcessActionSender;

public class ProcessActionReceiver extends AdminUserReceiver<ProcessActionRequestAdminSide> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.PROCESS_ACTION;
    }


    @Override
    protected void setData(Sender server, SocketMessageGeneric<ProcessActionRequestAdminSide> socketMsg, AdminUserReceiver<ProcessActionRequestAdminSide>.AdminUserInfo info) {
        var user = info.userInfo.tcpSocket;
        var request = new ProcessActionRequestServerSide(socketMsg.msg.processId, socketMsg.msg.action, info.adminInfo.id);

        var sender = new ProcessActionSender(server);
        sender.send(user, request);
    }
}
