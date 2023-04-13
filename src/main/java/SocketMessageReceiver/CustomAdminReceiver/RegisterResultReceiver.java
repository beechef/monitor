package SocketMessageReceiver.CustomAdminReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import SocketMessageReceiver.DataType.RegisterResultRequest;
import SocketMessageReceiver.SocketMessageReceiverCallBack;

public class RegisterResultReceiver extends SocketMessageReceiverCallBack<RegisterResultRequest> {

    public RegisterResultReceiver(ExecutableData<RegisterResultRequest> callback) {
        super(callback);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.REGISTER_RESULT;
    }
}
