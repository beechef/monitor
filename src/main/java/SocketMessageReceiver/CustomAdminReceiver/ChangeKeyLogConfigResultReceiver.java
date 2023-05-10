package SocketMessageReceiver.CustomAdminReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import SocketMessageReceiver.DataType.ChangeKeyLogConfigResult;
import SocketMessageReceiver.SocketMessageReceiverCallBack;

public class ChangeKeyLogConfigResultReceiver extends SocketMessageReceiverCallBack<ChangeKeyLogConfigResult> {
    public ChangeKeyLogConfigResultReceiver(ExecutableData<ChangeKeyLogConfigResult> callback) {
        super(callback);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.CHANGE_KEY_LOG_CONFIG_RESULT;
    }
}
