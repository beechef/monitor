package Server.ServerInstance;

import Server.EventDispatcher.*;
import Server.ServerInstance.Pooling.BufferPooling;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TCPServer implements Server {
    private static final String LOCAL_HOST = "127.0.0.1";
    private static final int DEFAULT_POOL_SIZE = 3;
    private static final int DEFAULT_MAXIMUM_SIZE = 10;
    private static final int DEFAULT_ALIVE_TIME = 60 * 5;

    private final int _port;
    private int _buffer = 1024;
    private BufferPooling _bufferPooling;

    private ThreadPoolExecutor _executor;
    private AsynchronousServerSocketChannel _server;

    private final List<AsynchronousSocketChannel> _clients = new ArrayList<>();
    private final List<MiddlewareSocketMessageEvent> _middleWares = new ArrayList<>();

    private final List<Executable> _onStartEvents = new ArrayList<>();
    private final List<Executable> _onStopEvents = new ArrayList<>();

    public TCPServer(int port) {
        _port = port;
        _executor = CreateDefaultExecutor();
    }

    //region Builder

    private ThreadPoolExecutor CreateDefaultExecutor() {
        return new ThreadPoolExecutor(DEFAULT_POOL_SIZE, DEFAULT_MAXIMUM_SIZE, DEFAULT_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), runnable -> {
            Thread thread = new Thread(runnable);
            thread.setDaemon(false);
            return thread;
        }, (runnable, threadPoolExecutor) -> {
        });
    }

    protected AsynchronousServerSocketChannel CreateServer(int port, ThreadPoolExecutor executor) throws IOException {
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(AsynchronousChannelGroup.withThreadPool(executor));
        server.bind(new InetSocketAddress(LOCAL_HOST, port));

        return server;
    }

    public TCPServer SetExecutor(ThreadPoolExecutor executor) {
        _executor = executor;
        return this;
    }

    public TCPServer SetBuffer(int buffer) {
        _buffer = buffer;
        return this;
    }

    public TCPServer addMiddleware(MiddlewareSocketMessageEvent event) {
        _middleWares.add(event);
        return this;
    }

    public TCPServer removeMiddleware(MiddlewareSocketMessageEvent event) {
        _middleWares.remove(event);
        return this;
    }

    //endregion

    private void acceptConnection() {
        _server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel client, Void attachment) {
                acceptConnection();

                ByteBuffer buffer = _bufferPooling.get();
                readData(client, buffer);
                _clients.add(client);

                try {
                    System.out.println("New Connection: " + client.getRemoteAddress());
                } catch (IOException ignored) {
                }
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
            }
        });
    }

    private boolean emitMiddleWares(SocketMessage msg) {
        for (MiddlewareSocketMessageEvent middleWare : _middleWares) {
            if (!middleWare.execute(msg)) return false;
        }

        return true;
    }

    private void emitEventDispatcher(SocketMessage msg) {
        EventDispatcher.emitEvent(msg);
    }

    private void emitMessage(SocketMessage msg) {
        var canNext = emitMiddleWares(msg);
        if (canNext) emitEventDispatcher(msg);
    }

    private void readData(AsynchronousSocketChannel client, ByteBuffer buffer) {
        client.read(buffer, null, new CompletionHandler<Integer, Void>() {
            @Override
            public void completed(Integer numBytes, Void attachment) {
                if (numBytes == -1) {
                    try {
                        removeClient(client, buffer);
                    } catch (IOException ignored) {
                    }
                    return;
                }

                byte[] data = new byte[numBytes];
                System.arraycopy(buffer.array(), 0, data, 0, numBytes);

                try {
                    SocketMessage msg = new SocketMessage(client, data);
                    emitMessage(msg);
                } catch (IOException | ClassNotFoundException ignored) {
                }

                buffer.clear();
                readData(client, buffer);
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                try {
                    removeClient(client, buffer);
                } catch (IOException ignored) {
                }
            }
        });
    }

    private void removeClient(AsynchronousSocketChannel client, ByteBuffer buffer) throws IOException {
        _clients.remove(client);
        _bufferPooling.returnPool(buffer);

        System.out.println(client.getLocalAddress() + " is Disconnected");
    }

    public void start() throws IOException, SQLException, ClassNotFoundException {
        _executor.prestartCoreThread();
        _server = CreateServer(_port, _executor);
        _bufferPooling = new BufferPooling(_buffer);

        acceptConnection();

        onStart();
    }

    public void onStart() throws SQLException, ClassNotFoundException {
        for (Executable event : _onStartEvents) {
            event.execute();
        }
    }

    public void addOnStart(Executable event) {
        _onStartEvents.add(event);
    }

    public void addOnStop(Executable event) {
        _onStopEvents.add(event);
    }


    public void stop() throws IOException, SQLException, ClassNotFoundException {
        _server.close();

        onStop();
    }

    public void onStop() throws SQLException, ClassNotFoundException {
        for (Executable event : _onStopEvents) {
            event.execute();
        }
    }


    public void send(Object target, Message msg) {
        if (!(target instanceof AsynchronousSocketChannel client)) return;

        byte[] msgBytes = msg.toBytes();
        ByteBuffer buffer = _bufferPooling.get();
        buffer.put(msgBytes);
        buffer.flip();

        client.write(buffer);
        onSend(client, msg);
    }

    public void onSend(AsynchronousSocketChannel client, Message msg) {
    }

    public void broadCast(Message msg) {

    }

}
