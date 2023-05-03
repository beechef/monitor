package Client;

import Client.GUI.Admin.LoginGUI;
import Client.GUI.Admin.RegisterGUI;
import Client.GUI.Lib.ClientDTO;
import Client.GUI.Lib.GlobalVariable;
import Server.EventDispatcher.EventDispatcher;
import SocketMessageReceiver.CustomAdminReceiver.*;
import SocketMessageReceiver.DataType.GetImage.GetImageRequestAdminSide;
import SocketMessageSender.CustomAdminSender.GetImageSender;

import java.io.IOException;
import java.util.ArrayList;

public class Admin {

    public static void main(String[] args) throws IOException, InterruptedException {
        var tcpClient = new TCPClient("localhost", 4445);
        tcpClient.setBuffer(1024 * 1024);
        tcpClient.start();

        var udpClient = new UDPClient("localhost", 4446);
        udpClient.setBuffer(1024 * 1024);
        udpClient.start();

        ClientInstance.tcpClient = tcpClient;
        ClientInstance.udpClient = udpClient;

        GlobalVariable.LoginAdminGUI = new LoginGUI();
        GlobalVariable.RegisterAdminGUI = new RegisterGUI();
        GlobalVariable.LoginAdminGUI.setVisible(true);

//        java.awt.EventQueue.invokeLater(() -> new LoginGUI().setVisible(true));
        EventDispatcher.startListening(new GetUserResultReceiver(data -> {
            for (var user : data.userInfos) {

                boolean stmpStatus = false;
                if (user.status.toString().equals("AVAILABLE")) {
                    stmpStatus = true;
                }

                GlobalVariable.clientList.add(new ClientDTO(user.name, user.uuid, user.host, stmpStatus, user.port));
            }
            GlobalVariable.listClient.renderTable(GlobalVariable.clientList);
        }));

        EventDispatcher.startListening(new LoginUserResultReceiver(data -> {
            var userInfo = data.userInfo;

            boolean stmpStatus = false;
            System.out.println(userInfo.status);
            if (userInfo.status.toString().equals("AVAILABLE")) {
                stmpStatus = true;
            }

            System.out.println(userInfo.host);

            GlobalVariable.clientList.add(new ClientDTO(userInfo.name, userInfo.uuid, userInfo.host, stmpStatus, userInfo.port));
            GlobalVariable.listClient.renderTable(GlobalVariable.clientList);

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

        var bytes = new ArrayList<Byte>();

        EventDispatcher.startListening(new GetImageResultReceiver((data) -> {
            if (!data.isEnd) {
                for (var b : data.image) {
                    bytes.add(b);
                }
            } else {
                var image = new byte[bytes.size()];
                for (int i = 0; i < bytes.size(); i++) {
                    image[i] = bytes.get(i);
                }

                System.out.println("Image size: " + image.length);

                try {
                    var imageFile = new java.io.File("image.png");
                    var imageOutputStream = new java.io.FileOutputStream(imageFile);
                    imageOutputStream.write(image);
                    imageOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                bytes.clear();
            }
        }));


        Thread.currentThread().join();
    }
}
