package SocketMessageReceiver.CustomServerReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import SocketMessageReceiver.DataType.LoginRequest;
import SocketMessageReceiver.FilteredSocketMessageReceiver;

public class LoginReceiver extends FilteredSocketMessageReceiver<LoginRequest> {

    @Override
    protected void onExecute(SocketMessageGeneric<LoginRequest> socketMsg) {
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


