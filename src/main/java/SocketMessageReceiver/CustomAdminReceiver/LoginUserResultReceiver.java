package SocketMessageReceiver.CustomAdminReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import SocketMessageReceiver.DataType.LoginUserResult;
import SocketMessageReceiver.SocketMessageReceiverCallBack;

public class LoginUserResultReceiver extends SocketMessageReceiverCallBack<LoginUserResult> {
    public LoginUserResultReceiver(ExecutableData<LoginUserResult> callback) {
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
