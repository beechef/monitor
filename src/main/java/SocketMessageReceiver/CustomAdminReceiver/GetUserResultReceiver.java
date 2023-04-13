package SocketMessageReceiver.CustomAdminReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.GetUserResultRequest;
import SocketMessageReceiver.SocketMessageReceiverCallBack;

public class GetUserResultReceiver extends SocketMessageReceiverCallBack<GetUserResultRequest> {
    public GetUserResultReceiver(ExecutableData<GetUserResultRequest> callback) {
        super(callback);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_RESULT;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<GetUserResultRequest> socketMsg) {
        var userInfos = socketMsg.msg.userInfos;


    }
}
