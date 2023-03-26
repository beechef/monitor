package Server.ServerInstance.Pooling;

import java.util.LinkedList;
import java.util.Queue;

public class QueuePooling<T> implements Pooling<T> {
    private final Queue<T> data = new LinkedList<>();
    private final PoolingFactory<T> _factory;

    public QueuePooling(PoolingFactory<T> factory) {
        this._factory = factory;
    }

    @Override
    public T get() {
        if (data.isEmpty()) data.add(_factory.create());

        return data.remove();
    }

    @Override
    public void returnPool(T item) {
        if (item == null) return;

        data.add(item);
    }
}
