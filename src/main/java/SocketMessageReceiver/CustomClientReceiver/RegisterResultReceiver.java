package SocketMessageReceiver.CustomClientReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.RegisterResultRequest;
import SocketMessageReceiver.FilteredSocketMessageReceiver;

public class RegisterResultReceiver extends FilteredSocketMessageReceiver<RegisterResultRequest> {
    private ExecutableData<RegisterResultRequest> callback;

    public RegisterResultReceiver(Sender sender, ExecutableData<RegisterResultRequest> callback) {
        super(sender);
        this.callback = callback;
    }

    @Override
    protected void onExecute(SocketMessageGeneric<RegisterResultRequest> socketMsg) {
        if (callback != null) callback.execute(socketMsg.msg);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.Connection.REGISTER_RESULT;
    }
}
