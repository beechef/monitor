package SocketMessageReceiver;

import Server.EventDispatcher.SocketMessage;
import Server.EventDispatcher.SocketMessageGeneric;

import java.lang.reflect.ParameterizedType;

public abstract class FilteredSocketMessageReceiver<T> extends SocketMessageReceiver {
    public Class<T> getFilterClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void execute(SocketMessage socketMsg) {
        var isFilteredMessage = isFilteredMessage(socketMsg);

        if (!isFilteredMessage) return;
        onExecute(filterMessage(socketMsg));
    }

    private SocketMessageGeneric<T> filterMessage(SocketMessage socketMsg) {
        return new SocketMessageGeneric<T>(socketMsg.sender, (T) socketMsg.msg.data);
    }

    protected abstract void onExecute(SocketMessageGeneric<T> socketMsg);

    private boolean isFilteredMessage(SocketMessage socketMsg) {
        var clazz = getFilterClass();

        return clazz.isInstance(socketMsg.msg.data);
    }
}
