package SocketMessageReceiver.CustomServerReceiver.GetImage;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.CustomServerReceiver.AdminUserReceiver;
import SocketMessageReceiver.DataType.GetImage.GetImageRequestAdminSide;
import SocketMessageReceiver.DataType.GetImage.GetImageRequestServerSide;
import SocketMessageSender.CustomServerSender.GetImageSender;

public class GetImageReceiver extends AdminUserReceiver<GetImageRequestAdminSide> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_IMAGE;
    }

    @Override
    protected void setData(Sender server, SocketMessageGeneric<GetImageRequestAdminSide> socketMsg, AdminUserReceiver<GetImageRequestAdminSide>.AdminUserInfo info) {
        var user = info.userInfo;
        var userSocket = user.udpSocket;

        var sender = new GetImageSender(server);
        sender.send(userSocket, new GetImageRequestServerSide(info.adminInfo.id));
    }
}
