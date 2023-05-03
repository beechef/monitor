package SocketMessageReceiver.CustomServerReceiver;

import Key.JWTKey;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.LoginAdminUdpRequest;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomAdminSender.LoginAdminUdpSender;
import io.jsonwebtoken.Jwts;

public class LoginAdminUdpReceiver extends SocketMessageReceiver<LoginAdminUdpRequest> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.LOGIN_UDP;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<LoginAdminUdpRequest> socketMsg) {
        var admin = socketMsg.sender;
        var jwt = Jwts.parserBuilder().setSigningKey(JWTKey.getKey()).build().parseClaimsJws(socketMsg.msg.getToken());
        var adminId = jwt.getBody().get("id", Integer.class);

        var adminInfo = UserController.getAdmin(adminId);
        if (adminInfo != null) {
            adminInfo.udpSocket = admin;
        }
    }
}
