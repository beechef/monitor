package SocketMessageReceiver.CustomServerReceiver;

import Key.JWTKey;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.GetUserRequest;
import SocketMessageReceiver.DataType.GetUserResultRequest;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.GetUserResultSender;
import io.jsonwebtoken.Jwts;

public class GetUserReceiver extends SocketMessageReceiver<GetUserRequest> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<GetUserRequest> socketMsg) {
        var admin = socketMsg.sender;
        var jwt = Jwts.parserBuilder().setSigningKey(JWTKey.getKey()).build().parseClaimsJws(socketMsg.msg.getToken());
        var adminId = jwt.getBody().get("id", Integer.class);

        var users = UserController.getTcpUsers(adminId);

        var sender = new GetUserResultSender(server);
        sender.send(admin, new GetUserResultRequest(users));
    }
}
