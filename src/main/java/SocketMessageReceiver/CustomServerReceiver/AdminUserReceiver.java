package SocketMessageReceiver.CustomServerReceiver;

import Key.JWTKey;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.MiddleWare.AdminUserRequest;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.SocketMessageReceiver;
import io.jsonwebtoken.Jwts;

public abstract class AdminUserReceiver<T> extends SocketMessageReceiver<T> {
    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<T> socketMsg) {
        if (!(socketMsg.msg instanceof AdminUserRequest request)) return;

        var jwt = Jwts.parserBuilder().setSigningKey(JWTKey.getKey()).build().parseClaimsJws(request.getToken());
        var adminId = jwt.getBody().get("id", Integer.class);
        var uuid = request.getUserUuid();

        var user = UserController.getUser(adminId, uuid);
        var admin = UserController.getAdmin(adminId);

        if (user != null && admin != null) {
            setData(server, socketMsg, new AdminUserInfo(admin, user));
        }
    }

    protected abstract void setData(Sender server, SocketMessageGeneric<T> socketMsg, AdminUserInfo info);

    public class AdminUserInfo {
        public UserController.AdminInfo adminInfo;
        public UserController.UserInfo userInfo;

        public AdminUserInfo(UserController.AdminInfo adminInfo, UserController.UserInfo userInfo) {
            this.adminInfo = adminInfo;
            this.userInfo = userInfo;
        }
    }
}
