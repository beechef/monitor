package SocketMessageSender.CustomServerSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.GetImage.GetImageResultServerSide;
import SocketMessageSender.SocketMessageSender;

public class GetImageResultSender extends SocketMessageSender<GetImageResultServerSide> {
    public GetImageResultSender(Sender server) {
        super(server);
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
