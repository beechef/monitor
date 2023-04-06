package SocketMessageReceiver.CustomClientReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import SocketMessageReceiver.DataType.LoginResultRequest;
import SocketMessageReceiver.SocketMessageReceiverCallBack;

public class LoginResultReceiver extends SocketMessageReceiverCallBack<LoginResultRequest> {

    public LoginResultReceiver(ExecutableData<LoginResultRequest> callback) {
        super(callback);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.Connection.LOGIN_RESULT;
    }
}

