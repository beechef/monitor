package SocketMessageReceiver.CustomAdminReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import SocketMessageReceiver.DataType.LoginUserResultAdminSide;
import SocketMessageReceiver.SocketMessageReceiverCallBack;

public class LoginUserResultReceiver extends SocketMessageReceiverCallBack<LoginUserResultAdminSide> {
    public LoginUserResultReceiver(ExecutableData<LoginUserResultAdminSide> callback) {
        super(callback);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserConnection.LOGIN_RESULT;
    }

}
