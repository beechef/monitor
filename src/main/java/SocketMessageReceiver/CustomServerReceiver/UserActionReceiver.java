package SocketMessageReceiver.CustomServerReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.UserActionRequestAdminSide;
import SocketMessageReceiver.DataType.UserActionRequestServerSide;
import SocketMessageSender.CustomServerSender.UserActionSender;

public class UserActionReceiver extends AdminUserReceiver<UserActionRequestAdminSide>{
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserConnection.ACTION;
    }

    @Override
    protected void setData(Sender server, SocketMessageGeneric<UserActionRequestAdminSide> socketMsg, AdminUserReceiver<UserActionRequestAdminSide>.AdminUserInfo info) {
        var sender = new UserActionSender(server);
        sender.send(info.userInfo.tcpSocket, new UserActionRequestServerSide(info.adminInfo.adminId, info.adminInfo.uuid, socketMsg.msg.action));
    }
}
