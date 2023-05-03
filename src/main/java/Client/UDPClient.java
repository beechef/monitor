package Client;

import Server.EventDispatcher.EventDispatcher;
import Server.EventDispatcher.SocketMessage;
import Server.ServerInstance.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient implements Client {
    private static final int DEFAULT_BUFFER = 1024;

    private int buffer = DEFAULT_BUFFER;
    private UDPClient _this;
    private DatagramSocket socket;
    private Thread receiveThread;

    private InetAddress serverAddress;
    private int port;

    public UDPClient(String hostName, int port) throws IOException {
        socket = new DatagramSocket();

        this.port = port;
        serverAddress = InetAddress.getByName(hostName);

        _this = this;
    }

    public UDPClient setBuffer(int buffer) {
        this.buffer = buffer;
        return this;
    }

    @Override
    public void send(Object target, Message msg) {
        var data = msg.toBytes();
        var packet = new DatagramPacket(data, data.length, serverAddress, port);

        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readData() throws IOException, ClassNotFoundException {
        var packet = new DatagramPacket(new byte[buffer], buffer);
        socket.receive(packet);

        SocketMessage msg = new SocketMessage(null, packet.getData());
        EventDispatcher.emitEvent(_this, msg);
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

    public void start() {
        createReceiveThread();
    }

    public void stop() {
        socket.close();
        receiveThread.interrupt();
    }

    @Override
    public void broadCast(Message msg) {

    }

    @Override
    public int getBuffer() {
        return buffer;
    }
}
