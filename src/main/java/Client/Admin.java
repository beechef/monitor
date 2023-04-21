package Client;

import Client.GUI.Admin.LoginGUI;
import Server.EventDispatcher.EventDispatcher;
import SocketMessageReceiver.CustomAdminReceiver.GetHardwareInfoResultReceiver;
import SocketMessageReceiver.CustomAdminReceiver.GetProcessesResultReceiver;

import java.io.IOException;

public class Admin {
    public static void main(String[] args) throws IOException, InterruptedException {
        var client = new ClientTCP("localhost", 4445);
        client.setBuffer(1024 * 16);
        client.start();

        ClientInstance.tcpClient = client;

        java.awt.EventQueue.invokeLater(() -> new LoginGUI().setVisible(true));

        EventDispatcher.startListening(new GetHardwareInfoResultReceiver(data -> {

            for (var hardwareInfo : data.hardwareInfos) {
                System.out.println(hardwareInfo);
            }
        }));


        EventDispatcher.startListening(new GetProcessesResultReceiver(data -> {
            for (var process : data.processes) {
                System.out.println("Process ID: " + process.id);
                System.out.println("Process Name: " + process.name);
                System.out.println("Process Path: " + process.path);
                System.out.println();
            }
        }));

        Thread.currentThread().join();
    }
}
