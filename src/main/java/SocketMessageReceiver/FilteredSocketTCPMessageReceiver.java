package SocketMessageReceiver;

import Server.EventDispatcher.SocketMessage;
import Server.EventDispatcher.SocketTCPMessageGeneric;
import Server.ServerInstance.Server;

import java.lang.reflect.ParameterizedType;

public abstract class FilteredSocketTCPMessageReceiver<T> extends SocketMessageReceiver {

    public FilteredSocketTCPMessageReceiver(Server server) {
        super(server);
    }

    public Class<T> getFilterClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void execute(SocketMessage socketMsg) {
        var isFilteredMessage = isFilteredMessage(socketMsg);

        if (!isFilteredMessage) return;
        onExecute(filterMessage(socketMsg));
    }

    private SocketTCPMessageGeneric<T> filterMessage(SocketMessage socketMsg) {
        return new SocketTCPMessageGeneric<T>(socketMsg.sender, (T) socketMsg.msg.data);
    }

    protected abstract void onExecute(SocketTCPMessageGeneric<T> socketMsg);

    private boolean isFilteredMessage(SocketMessage socketMsg) {
        var clazz = getFilterClass();

        return clazz.isInstance(socketMsg.msg.data);
    }
}
