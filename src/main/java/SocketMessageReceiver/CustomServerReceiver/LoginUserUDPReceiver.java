package SocketMessageReceiver.CustomServerReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.LoginUserUDPRequest;
import SocketMessageReceiver.SocketMessageReceiver;

public class LoginUserUDPReceiver extends SocketMessageReceiver<LoginUserUDPRequest> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserConnection.LOGIN_UDP;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<LoginUserUDPRequest> socketMsg) {
        var user = socketMsg.sender;
        var adminId = socketMsg.msg.adminId;
        var deviceId = socketMsg.msg.deviceId;

        var userInfo = UserController.getUser(adminId, deviceId);
        if (userInfo != null) {
            userInfo.udpSocket = user;
        }
    }

}
