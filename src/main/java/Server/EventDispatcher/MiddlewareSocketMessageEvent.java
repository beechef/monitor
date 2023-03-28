package Server.EventDispatcher;

public interface MiddlewareSocketMessageEvent {
    boolean execute(SocketMessage data);
}
