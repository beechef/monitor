package SocketMessageReceiver.CustomServerReceiver;

import Server.Database.CombineCondition;
import Server.Database.Condition;
import Server.Database.DatabaseConnector;
import Server.Database.Operator;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketTCPMessageGeneric;
import Server.ServerInstance.Message;
import Server.ServerInstance.Server;
import SocketMessageReceiver.DataType.RegisterRequest;
import SocketMessageReceiver.FilteredSocketTCPMessageReceiver;

import java.sql.SQLException;

public class RegisterReceiver extends FilteredSocketTCPMessageReceiver<RegisterRequest> {
    private static final String CLIENT_TABLE = "client";

    public RegisterReceiver(Server server) {
        super(server);
    }

    @Override
    protected void onExecute(SocketTCPMessageGeneric<RegisterRequest> socketMsg) {
        var existConditions = new Condition[]{
                new Condition("username", Operator.Equal, socketMsg.msg.username, CombineCondition.OR),
                new Condition("email", Operator.Equal, socketMsg.msg.email, CombineCondition.NONE),
        };
        try {
            var existUser = DatabaseConnector.select(CLIENT_TABLE, null, existConditions);

            if (existUser.next()) server.send(socketMsg.sender, new Message((byte) 1, (byte) 1, "EXIST"));
            else server.send(socketMsg.sender, new Message((byte) 1, (byte) 1, "OKAY"));
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
