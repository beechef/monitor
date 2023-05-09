package SocketMessageReceiver.CustomServerReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.LogOutAdminRequest;

public class LogOutAdminReceiver extends AdminUserReceiver<LogOutAdminRequest>{
    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.LOGOUT;
    }

    @Override
    protected void setData(Sender server, SocketMessageGeneric<LogOutAdminRequest> socketMsg, AdminUserReceiver<LogOutAdminRequest>.AdminUserInfo info) {
        UserController.removeAdmin(info.adminInfo);
    }
}
