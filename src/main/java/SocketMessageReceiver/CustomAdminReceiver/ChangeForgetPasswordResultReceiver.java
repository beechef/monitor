package SocketMessageReceiver.CustomAdminReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import SocketMessageReceiver.DataType.ChangeForgetPasswordResult;
import SocketMessageReceiver.SocketMessageReceiverCallBack;

public class ChangeForgetPasswordResultReceiver extends SocketMessageReceiverCallBack<ChangeForgetPasswordResult> {
    public ChangeForgetPasswordResultReceiver(ExecutableData<ChangeForgetPasswordResult> callback) {
        super(callback);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.CHANGE_FORGET_PASSWORD_RESULT;
    }
}
