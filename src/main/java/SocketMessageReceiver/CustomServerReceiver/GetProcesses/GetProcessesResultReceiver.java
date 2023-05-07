package SocketMessageReceiver.CustomServerReceiver.GetProcesses;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.GetProcessesResult.GetProcessesResultServerSide;
import SocketMessageReceiver.DataType.GetProcessesResult.GetProcessesResultUserSide;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.GetProcesses.GetProcessesResultSender;

public class GetProcessesResultReceiver extends SocketMessageReceiver<GetProcessesResultUserSide> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_PROCESSES_RESULT;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<GetProcessesResultUserSide> socketMsg) {
        var adminId = socketMsg.msg.adminId;
        var adminUuid = socketMsg.msg.adminUuid;

        var processes = socketMsg.msg.processes;
        var admin = UserController.getAdmin(adminId, adminUuid);

        if (admin != null) {
            var sender = new GetProcessesResultSender(server);
            sender.send(admin.tcpSocket, new GetProcessesResultServerSide(processes));
        }
    }
}
