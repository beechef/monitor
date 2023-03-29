package SocketMessageReceiver.CustomServerReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketTCPMessageGeneric;
import Server.ServerInstance.Server;
import SocketMessageReceiver.DataType.LoginRequest;
import SocketMessageReceiver.FilteredSocketTCPMessageReceiver;

public class LoginReceiver extends FilteredSocketTCPMessageReceiver<LoginRequest> {

    public LoginReceiver(Server server) {
        super(server);
    }

    @Override
    protected void onExecute(SocketTCPMessageGeneric<LoginRequest> socketMsg) {
        System.out.println(socketMsg.msg.username);
        System.out.println(socketMsg.msg.password);
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


