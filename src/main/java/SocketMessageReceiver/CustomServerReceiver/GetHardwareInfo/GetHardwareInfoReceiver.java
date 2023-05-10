package SocketMessageReceiver.CustomServerReceiver.GetHardwareInfo;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.CustomServerReceiver.AdminUserReceiver;
import SocketMessageReceiver.DataType.GetHardwareInfo.GetHardwareInfoAdminSide;
import SocketMessageReceiver.DataType.GetHardwareInfo.GetHardwareInfoServerSide;
import SocketMessageSender.CustomServerSender.GetHardwareInfo.GetHardwareInfoSender;

public class GetHardwareInfoReceiver extends AdminUserReceiver<GetHardwareInfoAdminSide> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_HARDWARE_INFO;
    }

    @Override
    protected void setData(Sender server, SocketMessageGeneric<GetHardwareInfoAdminSide> socketMsg, AdminUserReceiver<GetHardwareInfoAdminSide>.AdminUserInfo info) {
        var sender = new GetHardwareInfoSender(server);
        var id = info.adminInfo.adminId;
        var uuid = info.adminInfo.uuid;
        var hardwareTypes = socketMsg.msg.hardwareTypes;

        sender.send(info.userInfo.tcpSocket, new GetHardwareInfoServerSide(id, uuid, hardwareTypes));
    }
}
