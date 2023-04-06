package SocketMessageReceiver;

import Server.EventDispatcher.ExecutableData;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;

public abstract class SocketMessageReceiverCallBack<T> extends SocketMessageReceiver<T> {
    protected ExecutableData<T> callback;

    public SocketMessageReceiverCallBack(ExecutableData<T> callback) {
        this.callback = callback;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<T> socketMsg) {
        if (callback != null) callback.execute(socketMsg.msg);
    }
}
