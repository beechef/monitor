package SocketMessageReceiver;

import Server.EventDispatcher.SocketMessage;
import Server.EventDispatcher.SocketMessageEvent;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;

import java.lang.reflect.ParameterizedType;

public abstract class SocketMessageReceiver<T> implements SocketMessageEvent {
    public Class<T> getFilterClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void execute(Sender server, SocketMessage socketMsg) {
        var isFilteredMessage = isFilteredMessage(socketMsg);

        if (!isFilteredMessage) return;
        onExecute(server, filterMessage(socketMsg));
    }

    private SocketMessageGeneric<T> filterMessage(SocketMessage socketMsg) {
        return new SocketMessageGeneric<>(socketMsg.sender, (T) socketMsg.msg.data);
    }

    protected abstract void onExecute(Sender server, SocketMessageGeneric<T> socketMsg);

    private boolean isFilteredMessage(SocketMessage socketMsg) {
        var clazz = getFilterClass();

        return clazz.isInstance(socketMsg.msg.data);
    }
}
