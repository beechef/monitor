package SocketMessageReceiver.CustomServerReceiver;

import Server.Database.CombineCondition;
import Server.Database.Condition;
import Server.Database.DatabaseConnector;
import Server.Database.Operator;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.LoginRequest;
import SocketMessageReceiver.DataType.LoginResultRequest;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.LoginResultSender;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.sql.SQLException;

public class LoginReceiver extends SocketMessageReceiver<LoginRequest> {
    private static final String CLIENT_TABLE = "client";
    private static final String EMAIL_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";

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
                sender.send(target, new LoginResultRequest(LoginResultRequest.Result.EMAIL_NOT_EXIST));
                return;
            }

            var dbPassword = existUser.getString(PASSWORD_FIELD);
            if (!dbPassword.equals(password)) {
                sender.send(target, new LoginResultRequest(LoginResultRequest.Result.PASSWORD_WRONG));
                return;
            }

            sender.send(target, new LoginResultRequest(LoginResultRequest.Result.SUCCESS));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createToken(){
        var key = Keys.secretKeyFor(SignatureAlgorithm.RS256);

        var token = Jwts.builder()
                .setSubject("Joe")
                .claim("name", "Joe")
                .claim("scope", "self groups/admins")
                .signWith(key)
                .compact();
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.Connection.LOGIN;
    }
}


