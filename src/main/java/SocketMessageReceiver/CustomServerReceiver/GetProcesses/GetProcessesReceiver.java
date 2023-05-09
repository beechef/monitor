package SocketMessageReceiver.CustomServerReceiver.GetProcesses;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.CustomServerReceiver.AdminUserReceiver;
import SocketMessageReceiver.DataType.GetProcesses.GetProcessesAdminSide;
import SocketMessageReceiver.DataType.GetProcesses.GetProcessesServerSide;
import SocketMessageSender.CustomServerSender.GetProcesses.GetProcessesSender;

public class GetProcessesReceiver extends AdminUserReceiver<GetProcessesAdminSide> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_PROCESSES;
    }


    @Override
    protected void setData(Sender server, SocketMessageGeneric<GetProcessesAdminSide> socketMsg, AdminUserReceiver<GetProcessesAdminSide>.AdminUserInfo info) {
        var sender = new GetProcessesSender(server);
        sender.send(info.userInfo.tcpSocket, new GetProcessesServerSide(info.adminInfo.adminId, info.adminInfo.uuid));
    }
}
