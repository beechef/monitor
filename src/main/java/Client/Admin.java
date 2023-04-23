package Client;

import Client.GUI.Admin.LoginGUI;
import Server.EventDispatcher.EventDispatcher;
import SocketMessageReceiver.CustomAdminReceiver.*;

import java.io.IOException;

public class Admin {
    public static void main(String[] args) throws IOException, InterruptedException {
        var client = new ClientTCP("localhost", 4445);
        client.setBuffer(1024 * 16);
        client.start();

        ClientInstance.tcpClient = client;

        java.awt.EventQueue.invokeLater(() -> new LoginGUI().setVisible(true));

        EventDispatcher.startListening(new GetUserResultReceiver(data -> {
            for (var user : data.userInfos) {
                System.out.println("User UUID: " + user.uuid);
                System.out.println("User Name: " + user.name);
                System.out.println("User Host: " + user.host);
                System.out.println("User Port: " + user.port);
                System.out.println("User Status: " + user.status);
                System.out.println();
            }
        }));

        EventDispatcher.startListening(new LoginUserResultReceiver(data -> {
            var userInfo = data.userInfo;

            System.out.println("User UUID: " + userInfo.uuid);
            System.out.println("User Name: " + userInfo.name);
            System.out.println("User Host: " + userInfo.host);
            System.out.println("User Port: " + userInfo.port);
            System.out.println("User Status: " + userInfo.status);
            System.out.println();
        }));

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

        EventDispatcher.startListening(new ChangeUserNameResultReceiver(data -> {
            System.out.println("Change user name result:");
            System.out.println("UUID: " + data.uuid);
            System.out.println("Before name: " + data.beforeName);
            System.out.println("After name: " + data.afterName);
            System.out.println();
        }));

        Thread.currentThread().join();
    }
}
