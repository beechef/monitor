package Server.EventDispatcher;

public interface ExecutableEvent<T> {
    void execute(T data);
}
