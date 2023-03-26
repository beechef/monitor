package Server;

import Server.EventDispatcher.SocketMessage;
import Server.EventDispatcher.SocketMessageEvent;
import Server.ServerInstance.TCPServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        TCPServer server = new TCPServer(4445);
        server.addMiddleware(new SocketMessageEvent() {
            @Override
            public void execute(SocketMessage data) {
                System.out.println((String) data.msg.data);

                server.send(data.sender, data.msg);
            }
        });

        server.start();
        // Keep the server running
        Thread.currentThread().join();
    }
}
