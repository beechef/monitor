package SocketMessageReceiver.CustomAdminReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import SocketMessageReceiver.DataType.ChangeUserNameResult;
import SocketMessageReceiver.SocketMessageReceiverCallBack;

public class ChangeUserNameResultReceiver extends SocketMessageReceiverCallBack<ChangeUserNameResult> {
    public ChangeUserNameResultReceiver(ExecutableData<ChangeUserNameResult> callback) {
        super(callback);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.CHANGE_NAME_RESULT;
    }
}
