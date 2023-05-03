package SocketMessageReceiver.CustomServerReceiver.GetImage;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.GetImage.GetImageResultServerSide;
import SocketMessageReceiver.DataType.GetImage.GetImageResultUserSide;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.GetImageResultSender;

public class GetImageResultReceiver extends SocketMessageReceiver<GetImageResultUserSide> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_IMAGE_RESULT;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<GetImageResultUserSide> socketMsg) {
        var admin = UserController.getAdmin(socketMsg.msg.id);
        var sender = new GetImageResultSender(server);

        if (admin != null) {
            sender.send(admin.tcpSocket, new GetImageResultServerSide(socketMsg.msg.image, socketMsg.msg.session, socketMsg.msg.isEnd));
        }
    }
}
