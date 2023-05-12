package SocketMessageReceiver.CustomServerReceiver;

import Key.JWTKey;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.LogOutAdminRequest;
import SocketMessageReceiver.SocketMessageReceiver;
import io.jsonwebtoken.Jwts;

public class LogOutAdminReceiver extends SocketMessageReceiver<LogOutAdminRequest> {

    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.LOGOUT;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<LogOutAdminRequest> socketMsg) {
        try {
            var token = socketMsg.msg.getToken();
            if (token == null || token.equals("")) return;

            var jwt = Jwts.parserBuilder().setSigningKey(JWTKey.getKey()).build().parseClaimsJws(socketMsg.msg.getToken());
            var adminId = jwt.getBody().get(LoginReceiver.ID_FIELD, Integer.class);
            var adminUuid = jwt.getBody().get(LoginReceiver.UUID_FIELD, String.class);

            UserController.removeAdmin(adminId, adminUuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
