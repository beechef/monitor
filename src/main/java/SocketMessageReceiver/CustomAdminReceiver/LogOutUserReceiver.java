package SocketMessageReceiver.CustomAdminReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import SocketMessageReceiver.DataType.LogOutUserRequest;
import SocketMessageReceiver.SocketMessageReceiverCallBack;

public class LogOutUserReceiver extends SocketMessageReceiverCallBack<LogOutUserRequest> {
    public LogOutUserReceiver(ExecutableData<LogOutUserRequest> callback) {
        super(callback);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserConnection.LOGOUT;
    }
}
