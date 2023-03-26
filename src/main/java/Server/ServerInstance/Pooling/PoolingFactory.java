package Server.ServerInstance.Pooling;

public interface PoolingFactory<T> {
    T create();
}
