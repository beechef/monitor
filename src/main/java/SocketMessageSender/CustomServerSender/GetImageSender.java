package SocketMessageSender.CustomServerSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.GetImage.GetImageRequestServerSide;
import SocketMessageSender.SocketMessageSender;

public class GetImageSender extends SocketMessageSender<GetImageRequestServerSide> {
    public GetImageSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_IMAGE;
    }
}
