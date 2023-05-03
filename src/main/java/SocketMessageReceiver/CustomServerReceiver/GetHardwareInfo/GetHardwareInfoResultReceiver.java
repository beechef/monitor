package SocketMessageReceiver.CustomServerReceiver.GetHardwareInfo;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.GetHardwareInfoResult.GetHardwareInfoResultServerSide;
import SocketMessageReceiver.DataType.GetHardwareInfoResult.GetHardwareInfoResultUserSide;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.GetHardwareInfo.GetHardwareInfoResultSender;

public class GetHardwareInfoResultReceiver extends SocketMessageReceiver<GetHardwareInfoResultUserSide> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_HARDWARE_INFO_RESULT;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<GetHardwareInfoResultUserSide> socketMsg) {
        var id = socketMsg.msg.id;
        var hardwareInfos = socketMsg.msg.hardwareInfos;
        var admin = UserController.getAdmin(id);

        if (admin != null) {
            var sender = new GetHardwareInfoResultSender(server);
            sender.send(admin.tcpSocket, new GetHardwareInfoResultServerSide(hardwareInfos));
        }
    }
}
