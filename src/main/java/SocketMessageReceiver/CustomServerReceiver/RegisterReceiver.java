package SocketMessageReceiver.CustomServerReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import SocketMessageReceiver.DataType.RegisterRequest;
import SocketMessageReceiver.FilteredSocketMessageReceiver;

public class RegisterReceiver extends FilteredSocketMessageReceiver<RegisterRequest> {
    @Override
    protected void onExecute(SocketMessageGeneric<RegisterRequest> socketMsg) {
        System.out.println(socketMsg.msg.username);
        System.out.println(socketMsg.msg.password);
        System.out.println(socketMsg.msg.email);
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
