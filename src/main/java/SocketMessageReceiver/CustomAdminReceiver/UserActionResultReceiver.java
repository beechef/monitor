package SocketMessageReceiver.CustomAdminReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import SocketMessageReceiver.DataType.UserActionResultServerSide;
import SocketMessageReceiver.SocketMessageReceiverCallBack;

public class UserActionResultReceiver extends SocketMessageReceiverCallBack<UserActionResultServerSide> {
    public UserActionResultReceiver(ExecutableData<UserActionResultServerSide> callback) {
        super(callback);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserConnection.ACTION_RESULT;
    }
}
