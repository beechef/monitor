package Server;

import Server.EventDispatcher.EventDispatcher;
import Server.ServerInstance.TCPServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        TCPServer server = new TCPServer(4445);

        server.addMiddleware(data -> {
            System.out.println((String) data.msg.data);

            server.send(data.sender, data.msg);

            return true;
        }).addMiddleware(data -> {
            if (data.msg.head == 10) return false;

            return true;
        }).addMiddleware(data -> {
            System.out.println("Passed");

            return true;
        });

        server.start();
        // Keep the server running
        Thread.currentThread().join();
    }
}
