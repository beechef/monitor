package Client;

import Client.GUI.Admin.LoginGUI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        var client = new ClientTCP("localhost", 4445);
        client.start();

        ClientInstance.tcpClient = client;

        java.awt.EventQueue.invokeLater(() -> new LoginGUI().setVisible(true));

        Thread.currentThread().join();
    }
}
