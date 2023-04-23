package SocketMessageReceiver.CustomServerReceiver;

import Key.JWTKey;
import Server.Database.CombineCondition;
import Server.Database.Condition;
import Server.Database.DatabaseConnector;
import Server.Database.Operator;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.GetUserRequest;
import SocketMessageReceiver.DataType.GetUserResultRequest;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.GetUserResultSender;
import io.jsonwebtoken.Jwts;

import java.sql.SQLException;
import java.util.ArrayList;

public class GetUserReceiver extends SocketMessageReceiver<GetUserRequest> {
    private static final String BINDING_TABLE = "binding";
    private static final String BINDING_ADMIN_ID_FIELD = "id_admin";
    private static final String BINDING_USER_ID_FIELD = "id_user";

    private static final String USER_TABLE = "user";
    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";

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

        var currentUsers = UserController.getTcpUsers(adminId);
        var existUsers = getExistUsers(adminId);
        currentUsers = combineUsers(currentUsers, existUsers);

        var sender = new GetUserResultSender(server);
        sender.send(admin, new GetUserResultRequest(currentUsers));
    }

    private ArrayList<UserController.UserInfo> combineUsers(ArrayList<UserController.UserInfo> currentUsers, ArrayList<UserController.UserInfo> existUsers) {
        var combinedUsers = new ArrayList<>(currentUsers == null ? new ArrayList<>() : currentUsers);

        for (var existUser : existUsers) {
            for (int i = 0; i < combinedUsers.size(); i++) {
                var currentUser = combinedUsers.get(i);
                if (existUser.uuid.equals(currentUser.uuid)) continue;

                combinedUsers.add(existUser);
                break;
            }
        }

        return combinedUsers;
    }

    private ArrayList<UserController.UserInfo> getExistUsers(int adminId) {
        var existUsers = new ArrayList<UserController.UserInfo>();

        var conditions = new Condition[]{new Condition(BINDING_ADMIN_ID_FIELD, Operator.Equal, Integer.toString(adminId), CombineCondition.NONE)};
        try {
            var result = DatabaseConnector.select(BINDING_TABLE, null, conditions);
            while (!result.isClosed() && result.next()) {
                var userId = result.getString(BINDING_USER_ID_FIELD);

                var userConditions = new Condition[]{new Condition(USER_ID, Operator.Equal, userId, CombineCondition.NONE)};
                var userResult = DatabaseConnector.select(USER_TABLE, null, userConditions);
                if (userResult.next()) {
                    var id = userResult.getString(USER_ID);
                    var name = userResult.getString(USER_NAME);
                    var user = new UserController.UserInfo(id, name, UserController.UserInfo.UserStatus.UNAVAILABLE, null, null);

                    existUsers.add(user);
                }

                userResult.close();
            }

            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return existUsers;
    }
}
