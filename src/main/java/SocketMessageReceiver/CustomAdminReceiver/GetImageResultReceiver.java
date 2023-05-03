package SocketMessageReceiver.CustomAdminReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import SocketMessageReceiver.DataType.GetImage.GetImageResultServerSide;
import SocketMessageReceiver.SocketMessageReceiverCallBack;

public class GetImageResultReceiver extends SocketMessageReceiverCallBack<GetImageResultServerSide> {
    public GetImageResultReceiver(ExecutableData<GetImageResultServerSide> callback) {
        super(callback);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_IMAGE_RESULT;
    }
}
