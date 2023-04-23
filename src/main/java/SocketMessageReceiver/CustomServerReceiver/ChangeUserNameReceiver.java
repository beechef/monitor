package SocketMessageReceiver.CustomServerReceiver;

import Key.JWTKey;
import Server.Database.*;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.ChangeUserNameRequest;
import SocketMessageReceiver.DataType.ChangeUserNameResult;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.ChangeUserNameResultSender;
import io.jsonwebtoken.Jwts;

import java.sql.SQLException;
import java.util.ArrayList;

public class ChangeUserNameReceiver extends SocketMessageReceiver<ChangeUserNameRequest> {
    public static final String USER_TABLE = "user";
    public static final String USER_ID_FIELD = "id";
    public static final String USER_NAME_FIELD = "name";

    public static final String BIDING_TABLE = "binding";
    public static final String BIDING_ADMIN_ID_FIELD = "id_admin";
    public static final String BIDING_USER_ID_FIELD = "id_user";

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.CHANGE_NAME;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<ChangeUserNameRequest> socketMsg) {
        var jwt = Jwts.parserBuilder().setSigningKey(JWTKey.getKey()).build().parseClaimsJws(socketMsg.msg.getToken());
        var adminId = jwt.getBody().get("id", Integer.class);
        var userId = socketMsg.msg.userId;
        var afterName = socketMsg.msg.name;

        try {
            var isBinding = isBinding(adminId, userId);
            if (!isBinding) return;

            var conditions = new Condition[]{new Condition(USER_ID_FIELD, Operator.Equal, userId, CombineCondition.NONE)};
            var fields = new String[]{USER_NAME_FIELD};
            var values = new String[]{afterName};
            var keyPairs = new ArrayList<KeyPair<String, String>>(fields.length);

            for (int i = 0; i < fields.length; i++) {
                keyPairs.add(new KeyPair<>(fields[i], values[i]));
            }

            var userInfo = DatabaseConnector.select(USER_TABLE, null, conditions);
            var beforeName = userId;
            if (userInfo.next()) {
                beforeName = userInfo.getString(USER_NAME_FIELD);
            }

            DatabaseConnector.update(USER_TABLE, keyPairs, conditions);

            var sender = new ChangeUserNameResultSender(server);
            sender.send(socketMsg.sender, new ChangeUserNameResult(userId, beforeName, afterName));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isBinding(int adminId, String userId) throws SQLException {
        var conditions = new Condition[]{
                new Condition(BIDING_ADMIN_ID_FIELD, Operator.Equal, Integer.toString(adminId), CombineCondition.AND),
                new Condition(BIDING_USER_ID_FIELD, Operator.Equal, userId, CombineCondition.NONE),
        };

        var result = DatabaseConnector.select(BIDING_TABLE, null, conditions);
        return result.next();
    }
}
