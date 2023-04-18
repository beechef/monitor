package SocketMessageReceiver.CustomAdminReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import SocketMessageReceiver.DataType.GetProcessesResult.GetProcessesResultServerSide;
import SocketMessageReceiver.SocketMessageReceiverCallBack;

public class GetProcessesResultReceiver extends SocketMessageReceiverCallBack<GetProcessesResultServerSide> {
    public GetProcessesResultReceiver(ExecutableData<GetProcessesResultServerSide> callback) {
        super(callback);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_PROCESSES_RESULT;
    }
}
