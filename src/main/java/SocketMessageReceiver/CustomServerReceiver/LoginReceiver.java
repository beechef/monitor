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
import SocketMessageReceiver.DataType.LoginRequest;
import SocketMessageReceiver.DataType.LoginResultRequest;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.LoginResultSender;
import io.jsonwebtoken.Jwts;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginReceiver extends SocketMessageReceiver<LoginRequest> {
    private static final String CLIENT_TABLE = "admin";
    private static final String ID_FIELD = "id";
    private static final String EMAIL_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";

    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 6;


    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<LoginRequest> socketMsg) {
        var email = socketMsg.msg.email;
        var password = socketMsg.msg.password;
        var sender = new LoginResultSender(server);
        var target = socketMsg.sender;

        var existConditions = new Condition[]{new Condition(EMAIL_FIELD, Operator.Equal, email, CombineCondition.NONE),};
        try {
            var existUser = DatabaseConnector.select(CLIENT_TABLE, null, existConditions);
            var isExistUser = existUser.next();

            if (!isExistUser) {
                sender.send(target, new LoginResultRequest(LoginResultRequest.Result.EMAIL_NOT_EXIST, null));
                return;
            }

            var dbPassword = existUser.getString(PASSWORD_FIELD);
            if (!dbPassword.equals(password)) {
                sender.send(target, new LoginResultRequest(LoginResultRequest.Result.PASSWORD_WRONG, null));
                return;
            }

            var token = createToken(existUser);
            sender.send(target, new LoginResultRequest(LoginResultRequest.Result.SUCCESS, token));

            UserController.addTcpAdmin(new UserController.AdminInfo(existUser.getInt(ID_FIELD), target));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String createToken(ResultSet user) throws SQLException {
        var key = JWTKey.getKey();
        var id = user.getInt(ID_FIELD);
        var email = user.getString(EMAIL_FIELD);

        return Jwts.builder()
                .claim(ID_FIELD, id)
                .claim(EMAIL_FIELD, email)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME))
                .signWith(key)
                .compact();
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.LOGIN;
    }
}


