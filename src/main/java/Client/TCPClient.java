package Client;

import Server.EventDispatcher.EventDispatcher;
import Server.EventDispatcher.SocketMessage;
import Server.ServerInstance.Message;
import Server.ServerInstance.Pooling.BufferPooling;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.LinkedList;
import java.util.Queue;

public class TCPClient implements Client {
    private static final int DEFAULT_BUFFER = 1024;

    private final AsynchronousSocketChannel socket;
    private final InetSocketAddress serverAddress;

    private BufferPooling bufferPooling;

    private int buffer = DEFAULT_BUFFER;
    private TCPClient _this;

    public TCPClient(String hostName, int port) throws IOException {
        serverAddress = new InetSocketAddress(hostName, port);
        socket = AsynchronousSocketChannel.open();

        _this = this;
    }

    public TCPClient setBuffer(int buffer) {
        this.buffer = buffer;
        return this;
    }

    public void start() {
        bufferPooling = new BufferPooling(buffer);

        socket.connect(serverAddress, null, new CompletionHandler<Void, Void>() {
            @Override
            public void completed(Void result, Void attachment) {
                System.out.println("Connected to server");
                ByteBuffer buffer = bufferPooling.get();

                readData(socket, buffer);
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.err.println("Failed to connect to server: " + exc.getMessage());
            }
        });
    }

    public void stop() throws IOException {
        socket.close();
    }

    private void readData(AsynchronousSocketChannel client, ByteBuffer buffer) {
        client.read(buffer, null, new CompletionHandler<Integer, Void>() {
            @Override
            public void completed(Integer numBytes, Void attachment) {
                if (numBytes == -1) {
                    return;
                }

                byte[] data = new byte[numBytes];
                System.arraycopy(buffer.array(), 0, data, 0, numBytes);

                try {
                    SocketMessage msg = new SocketMessage(client, data);
                    EventDispatcher.emitEvent(_this, msg);
                } catch (IOException | ClassNotFoundException ignored) {
                }

                buffer.clear();
                readData(client, buffer);
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.err.println("Failed to read data from server: " + exc.getMessage());
            }
        });
    }

    Queue<ByteBuffer> bufferQueue = new LinkedList<>();
    boolean sending = false;

    @Override
    public void send(Object target, Message msg) {
        byte[] msgBytes = msg.toBytes();
        ByteBuffer buffer = bufferPooling.get();
        buffer.put(msgBytes);
        buffer.flip();

        if (!sending) {
            bufferQueue.add(buffer);
            send();
        } else {
            bufferQueue.add(buffer);
        }
    }

    private void send() {
        ByteBuffer buffer = bufferQueue.remove();
        sending = true;

        socket.write(buffer, null, new CompletionHandler<>() {
            @Override
            public void completed(Integer result, Object attachment) {
                buffer.clear();

                if (bufferQueue.size() > 0) {
                    send();
                } else {
                    sending = false;
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {

            }
        });
    }

    @Override
    public void broadCast(Message msg) {
    }

    @Override
    public int getBuffer() {
        return buffer;
    }
}
