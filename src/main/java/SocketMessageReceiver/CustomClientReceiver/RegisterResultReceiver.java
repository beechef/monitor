package SocketMessageReceiver.CustomClientReceiver;

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
        return EventHeadByte.CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.Connection.REGISTER_RESULT;
    }
}
