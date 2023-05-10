package Client;

import Client.GUI.Admin.LoginGUI;
import Client.GUI.Admin.RegisterGUI;
import Client.GUI.Lib.ClientDTO;
import Client.GUI.Lib.GlobalVariable;
import Client.GUI.Lib.ProcessDTO;
import Server.EventDispatcher.EventDispatcher;
import SocketMessageReceiver.CustomAdminReceiver.*;
import jdk.jfr.Event;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Admin {

    public static void main(String[] args) throws IOException, InterruptedException {
//        try (ServerSocket ignored = new ServerSocket(9998)) {
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
            System.out.println("load user");
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
            System.out.println("user login");
            var userInfo = data.userInfo;

            GlobalVariable.listClient.handleUerLogin(userInfo.name, userInfo.uuid, userInfo.host, true, userInfo.port);

        }));

        EventDispatcher.startListening(new GetHardwareInfoResultReceiver(data -> {
            for (var hardwareInfo : data.hardwareInfos.entrySet()) {
                System.out.println("Hardware name: " + hardwareInfo.getKey());

                for (var info : hardwareInfo.getValue()) {
                    System.out.println("Key: " + info.key);
                    System.out.println("Value: " + info.value);
                }
            }
        }));

        EventDispatcher.startListening(new GetProcessesResultReceiver(data -> {
            GlobalVariable.processList.removeAll(GlobalVariable.processList);
            for (var process : data.processes) {
                GlobalVariable.processList.add(new ProcessDTO(process.name,process.id+"",process.path));
                System.out.println("Process ID: " + process.id);
                System.out.println("Process Name: " + process.name);
                System.out.println("Process Path: " + process.path);
                System.out.println();
            }
            GlobalVariable.process.renderProcess(GlobalVariable.processList);
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

        EventDispatcher.startListening(new ProcessActionResultReceiver(data -> {
            System.out.println("Process action result:");
            System.out.println("Process ID: " + data.processId);
            System.out.println("Action: " + data.action);
            System.out.println("Result: " + data.result);
            System.out.println("Message: " + data.message);
            System.out.println();
        }));

        EventDispatcher.startListening(new LogOutUserReceiver(data -> {
            System.out.println("Log out user result:");
            System.out.println("UUID: " + data.deviceId);
            System.out.println();

            GlobalVariable.listClient.handleUerLogout(data.deviceId);

        }));

        EventDispatcher.startListening(new ForgetPasswordResultReceiver(data -> {
            System.out.println("Forget password result:");
            System.out.println("Email: " + data.email);
            System.out.println("Result: " + data.result);
            System.out.println();
        }));

        EventDispatcher.startListening(new ChangeForgetPasswordResultReceiver(data -> {
            System.out.println("Change forget password result:");
            System.out.println("Result: " + data.result);
            System.out.println();
        }));

        Thread.currentThread().join();
//        } catch (IOException e) {
//            System.out.println("Application instance is already running.");
//            System.exit(0);
//        }

    }
}
