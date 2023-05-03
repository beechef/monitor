package SocketMessageReceiver.CustomUserReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.GetImage.GetImageRequestServerSide;
import SocketMessageReceiver.SocketMessageReceiver;

import java.awt.image.BufferedImage;

public class GetImageReceiver extends SocketMessageReceiver<GetImageRequestServerSide> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_IMAGE;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<GetImageRequestServerSide> socketMsg) {
        BufferedImage x;
    }
}
