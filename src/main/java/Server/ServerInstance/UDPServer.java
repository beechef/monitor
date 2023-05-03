package Server.ServerInstance;

import Server.EventDispatcher.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UDPServer implements Server {
    private static final String LOCAL_HOST = "127.0.0.1";
    private static final int DEFAULT_BUFFER = 1024;

    private final int port;
    private int buffer = DEFAULT_BUFFER;

    private DatagramSocket server;
    private Thread receiveThread;

    private final List<MiddlewareSocketMessageEvent> _middleWares = new ArrayList<>();

    private final List<Executable> _onStartEvents = new ArrayList<>();
    private final List<Executable> _onStopEvents = new ArrayList<>();

    public UDPServer(int port) throws SocketException {
        this.port = port;

        server = new DatagramSocket(port);
    }


    public UDPServer setBuffer(int buffer) {
        this.buffer = buffer;
        return this;
    }

    public void addMiddleware(MiddlewareSocketMessageEvent event) {
        _middleWares.add(event);
    }

    public void removeMiddleware(MiddlewareSocketMessageEvent event) {
        _middleWares.remove(event);
    }


    private boolean emitMiddleWares(SocketMessage msg) {
        for (MiddlewareSocketMessageEvent middleWare : _middleWares) {
            if (!middleWare.execute(msg)) return false;
        }

        return true;
    }

    private void emitEventDispatcher(SocketMessage msg) {
        EventDispatcher.emitEvent(this, msg);
    }

    private void emitMessage(SocketMessage msg) {
        var canNext = emitMiddleWares(msg);
        if (canNext) emitEventDispatcher(msg);
    }

    private void readData() throws IOException, ClassNotFoundException {
        var packet = new DatagramPacket(new byte[buffer], buffer);
        server.receive(packet);

        var client = new UDPClient(packet.getAddress(), packet.getPort());
        var data = packet.getData();
        SocketMessage msg = new SocketMessage(client, data);
        emitMessage(msg);
    }

    public void start() throws SQLException, ClassNotFoundException {
        createReceiveThread();
        onStart();
    }

    private void createReceiveThread() {
        receiveThread = new Thread(() -> {
            while (true) {
                try {
                    readData();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        receiveThread.start();
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
        server.close();
        receiveThread.interrupt();

        onStop();
    }

    public void onStop() throws SQLException, ClassNotFoundException {
        for (Executable event : _onStopEvents) {
            event.execute();
        }
    }


    public void send(Object target, Message msg) {
        if (!(target instanceof UDPClient client)) return;

        byte[] msgBytes = msg.toBytes();

        var packet = new DatagramPacket(msgBytes, msgBytes.length, client.address, client.port);
        try {
            server.send(packet);
            onSend(client, msg);
        } catch (IOException e) {
            removeClient(client);
        }

    }

    private void removeClient(Object client) {
        EventDispatcher.emitEvent(EventName.USER_DISCONNECTED, client);
    }

    public void onSend(UDPClient client, Message msg) {
    }

    public void broadCast(Message msg) {

    }

    @Override
    public int getBuffer() {
        return buffer;
    }

    public static class UDPClient {
        public InetAddress address;
        public int port;

        public UDPClient(InetAddress address, int port) {
            this.address = address;
            this.port = port;
        }
    }
}
