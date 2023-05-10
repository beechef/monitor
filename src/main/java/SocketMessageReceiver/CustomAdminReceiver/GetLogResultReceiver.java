package SocketMessageReceiver.CustomAdminReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import SocketMessageReceiver.DataType.GetLogResult;
import SocketMessageReceiver.SocketMessageReceiverCallBack;

public class GetLogResultReceiver extends SocketMessageReceiverCallBack<GetLogResult> {
    public GetLogResultReceiver(ExecutableData<GetLogResult> callback) {
        super(callback);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_LOG_RESULT;
    }

}
