package Server.ServerInstance.Pooling;

public interface Pooling<T> {
    T get();

    void returnPool(T item);
}
