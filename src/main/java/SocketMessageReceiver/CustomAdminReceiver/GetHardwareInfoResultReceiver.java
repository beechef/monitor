package SocketMessageReceiver.CustomAdminReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import SocketMessageReceiver.DataType.GetHardwareInfoResult.GetHardwareInfoResultServerSide;

public class GetHardwareInfoResultReceiver extends SocketMessageReceiver.SocketMessageReceiverCallBack<GetHardwareInfoResultServerSide> {
    public GetHardwareInfoResultReceiver(ExecutableData<GetHardwareInfoResultServerSide> callback) {
        super(callback);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_HARDWARE_INFO_RESULT;
    }

}
