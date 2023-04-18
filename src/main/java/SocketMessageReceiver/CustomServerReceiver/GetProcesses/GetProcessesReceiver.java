package SocketMessageReceiver.CustomServerReceiver.GetProcesses;

import Key.JWTKey;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.GetProcesses.GetProcessesAdminSide;
import SocketMessageReceiver.DataType.GetProcesses.GetProcessesServerSide;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.GetProcesses.GetProcessesSender;
import io.jsonwebtoken.Jwts;

public class GetProcessesReceiver extends SocketMessageReceiver<GetProcessesAdminSide> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_PROCESSES;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<GetProcessesAdminSide> socketMsg) {
        var jwt = Jwts.parserBuilder().setSigningKey(JWTKey.getKey()).build().parseClaimsJws(socketMsg.msg.getToken());
        var adminId = jwt.getBody().get("id", Integer.class);
        var uuid = socketMsg.msg.userUuid;

        var sender = new GetProcessesSender(server);
        var user = UserController.getTcpUser(adminId, uuid);
        var admin = UserController.getTcpAdmin(adminId, socketMsg.sender);

        if (user != null && admin != null) {
            var id = admin.id;

            sender.send(user.socket, new GetProcessesServerSide(id));
        }
    }
}
