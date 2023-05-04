package SocketMessageReceiver.CustomUserReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import SocketMessageReceiver.DataType.ProcessAction.ProcessActionRequestServerSide;
import SocketMessageReceiver.SocketMessageReceiverCallBack;

public class ProcessActionReceiver extends SocketMessageReceiverCallBack<ProcessActionRequestServerSide> {
    public ProcessActionReceiver(ExecutableData<ProcessActionRequestServerSide> callback) {
        super(callback);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.PROCESS_ACTION;
    }
}
