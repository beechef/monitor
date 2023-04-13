package SocketMessageReceiver.CustomServerReceiver;

import Server.Database.*;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.RegisterRequest;
import SocketMessageReceiver.DataType.RegisterResultRequest;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.RegisterResultSender;

import java.sql.SQLException;
import java.util.ArrayList;

public class RegisterReceiver extends SocketMessageReceiver<RegisterRequest> {
    private static final String CLIENT_TABLE = "client";
    private static final String EMAIL_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<RegisterRequest> socketMsg) {
        var email = socketMsg.msg.email;
        var password = socketMsg.msg.password;
        var target = socketMsg.sender;

        var existConditions = new Condition[]{
//                new Condition("username", Operator.Equal, socketMsg.msg.username, CombineCondition.OR),
                new Condition(EMAIL_FIELD, Operator.Equal, email, CombineCondition.NONE),
        };
        try {
            var existUser = DatabaseConnector.select(CLIENT_TABLE, null, existConditions);
            var result = RegisterResultRequest.Result.SUCCESS;
            var sender = new RegisterResultSender(server);
            var isExistEmail = existUser.next();
            var passwordCheckResult = checkPassword(password);
            var isPasswordValid = passwordCheckResult == RegisterResultRequest.Result.SUCCESS;

            if (isExistEmail) {
                result = RegisterResultRequest.Result.EMAIL_EXIST;
                sender.send(target, new RegisterResultRequest(result));
                return;
            }
            if (!isPasswordValid) {
                result = passwordCheckResult;
                sender.send(target, new RegisterResultRequest(result));
                return;
            }

            result = createAccount(socketMsg.msg) ? RegisterResultRequest.Result.SUCCESS : RegisterResultRequest.Result.UNKNOWN_ERROR;
            sender.send(target, new RegisterResultRequest(result));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private RegisterResultRequest.Result checkPassword(String password) {
        if (password.length() < 6) return RegisterResultRequest.Result.PASSWORD_LOWER_6_LENGTH;

        return RegisterResultRequest.Result.SUCCESS;
    }

    private boolean createAccount(RegisterRequest request) {
        var email = request.email;
        var password = request.password;

        var values = new ArrayList<KeyPair<String, String>>();
        values.add(new KeyPair<>(EMAIL_FIELD, email));
        values.add(new KeyPair<>(PASSWORD_FIELD, password));

        try {
            DatabaseConnector.insert(CLIENT_TABLE, values);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.REGISTER;
    }
}
