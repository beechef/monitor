package SocketMessageReceiver.CustomAdminReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import SocketMessageReceiver.DataType.ForgetPasswordResult;
import SocketMessageReceiver.SocketMessageReceiverCallBack;

public class ForgetPasswordResultReceiver extends SocketMessageReceiverCallBack<ForgetPasswordResult> {
    public ForgetPasswordResultReceiver(ExecutableData<ForgetPasswordResult> callback) {
        super(callback);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.FORGET_PASSWORD_RESULT;
    }
}
