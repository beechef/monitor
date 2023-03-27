package Server.EventDispatcher;

public interface ExecutableEvent<T> {
    boolean execute(T data);
}
