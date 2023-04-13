package Client;

import Client.GUI.Admin.LoginGUI;
import Server.EventDispatcher.EventDispatcher;
import SocketMessageReceiver.CustomAdminReceiver.GetHardwareInfoResultReceiver;

import java.io.IOException;

public class Admin {
    public static void main(String[] args) throws IOException, InterruptedException {
        var client = new ClientTCP("localhost", 4445);
        client.start();

        ClientInstance.tcpClient = client;

        java.awt.EventQueue.invokeLater(() -> new LoginGUI().setVisible(true));

        EventDispatcher.startListening(new GetHardwareInfoResultReceiver(data -> {

            for (var hardwareInfo : data.hardwareInfos) {
                System.out.println(hardwareInfo);
            }
        }));

        Thread.currentThread().join();
    }
}
