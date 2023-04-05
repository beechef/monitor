package SocketMessageReceiver.CustomServerReceiver;

import Server.Database.CombineCondition;
import Server.Database.Condition;
import Server.Database.DatabaseConnector;
import Server.Database.Operator;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Message;
import Server.ServerInstance.Server;
import SocketMessageReceiver.DataType.RegisterRequest;
import SocketMessageReceiver.DataType.RegisterResultRequest;
import SocketMessageReceiver.FilteredSocketMessageReceiver;
import SocketMessageSender.CustomServerSender.RegisterResultSender;

import java.sql.SQLException;

public class RegisterReceiver extends FilteredSocketMessageReceiver<RegisterRequest> {
    private static final String CLIENT_TABLE = "client";

    public RegisterReceiver(Server server) {
        super(server);
    }

    @Override
    protected void onExecute(SocketMessageGeneric<RegisterRequest> socketMsg) {
        var existConditions = new Condition[]{
//                new Condition("username", Operator.Equal, socketMsg.msg.username, CombineCondition.OR),
                new Condition("email", Operator.Equal, socketMsg.msg.email, CombineCondition.NONE),
        };
        try {
            var existUser = DatabaseConnector.select(CLIENT_TABLE, null, existConditions);

            var result = RegisterResultRequest.Result.SUCCESS;
            var sender = new RegisterResultSender(server);

            if (existUser.next()) result = RegisterResultRequest.Result.USERNAME_EXIST;
            sender.send(socketMsg.sender, new RegisterResultRequest(result));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.Connection.REGISTER;
    }
}
