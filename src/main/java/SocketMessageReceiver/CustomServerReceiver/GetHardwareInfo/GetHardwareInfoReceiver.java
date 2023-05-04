package SocketMessageReceiver.CustomServerReceiver.GetHardwareInfo;

import Key.JWTKey;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.GetHardwareInfo.GetHardwareInfoAdminSide;
import SocketMessageReceiver.DataType.GetHardwareInfo.GetHardwareInfoServerSide;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.GetHardwareInfo.GetHardwareInfoSender;
import io.jsonwebtoken.Jwts;

public class GetHardwareInfoReceiver extends SocketMessageReceiver<GetHardwareInfoAdminSide> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_HARDWARE_INFO;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<GetHardwareInfoAdminSide> socketMsg) {
        var jwt = Jwts.parserBuilder().setSigningKey(JWTKey.getKey()).build().parseClaimsJws(socketMsg.msg.getToken());
        var adminId = jwt.getBody().get("id", Integer.class);
        var uuid = socketMsg.msg.userUuid;
        var hardwareTypes = socketMsg.msg.hardwareTypes;

        var sender = new GetHardwareInfoSender(server);
        var user = UserController.getUser(adminId, uuid);
        var admin = UserController.getAdmin(adminId);

        if (user != null && admin != null) {
            var id = admin.adminId;

            sender.send(user.tcpSocket, new GetHardwareInfoServerSide(hardwareTypes, id));
        }
    }
}
