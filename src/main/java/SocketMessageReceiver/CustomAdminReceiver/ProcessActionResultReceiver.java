package SocketMessageReceiver.CustomAdminReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import SocketMessageReceiver.DataType.ProcessAction.ProcessActionResultUserSide;
import SocketMessageReceiver.SocketMessageReceiverCallBack;

public class ProcessActionResultReceiver extends SocketMessageReceiverCallBack<ProcessActionResultUserSide> {
    public ProcessActionResultReceiver(ExecutableData<ProcessActionResultUserSide> callback) {
        super(callback);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.PROCESS_ACTION_RESULT;
    }
}
