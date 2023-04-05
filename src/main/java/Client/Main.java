package Client;

import Client.GUI.Admin.RegisterGUI;
import Server.EventDispatcher.EventDispatcher;
import Server.EventDispatcher.EventHead.EventHeadByte;
import SocketMessageReceiver.CustomClientReceiver.RegisterResultReceiver;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        var client = new ClientTCP("localhost", 4445);
        client.start();

        ClientInstance.tcpClient = client;

        java.awt.EventQueue.invokeLater(() -> new RegisterGUI().setVisible(true));
        EventDispatcher.startListening(EventHeadByte.CONNECTION, EventHeadByte.Connection.REGISTER_RESULT, new RegisterResultReceiver(client, (data -> {
            System.out.println("Register result: " + data.result);
        })));

        Thread.currentThread().join();
    }
}
