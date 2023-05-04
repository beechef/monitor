package Client;

import Server.EventDispatcher.EventDispatcher;
import SocketMessageReceiver.CustomAdminReceiver.LoginUserResultReceiver;
import SocketMessageReceiver.CustomUserReceiver.GetHardwareInfoReceiver;
import SocketMessageReceiver.CustomUserReceiver.GetImageReceiver;
import SocketMessageReceiver.CustomUserReceiver.GetProcessesReceiver;
import SocketMessageReceiver.CustomUserReceiver.ProcessActionReceiver;
import SocketMessageReceiver.DataType.LoginUserRequest;
import SocketMessageReceiver.DataType.LoginUserUDPRequest;
import SocketMessageReceiver.DataType.ProcessAction.ProcessActionResultUserSide;
import SocketMessageSender.CustomUserSender.LoginSender;
import SocketMessageSender.CustomUserSender.LoginUDPSender;
import SocketMessageSender.CustomUserSender.ProcessActionResultSender;
import Utilities.Utilities;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;

import java.io.*;
import java.net.ServerSocket;
import java.nio.Buffer;

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

            var loginTcpSender = new LoginSender(tcpClient);
            var uuid = Utilities.getUUID();
            var adminId = 11;

            EventDispatcher.startListening(new LoginUserResultReceiver((loginUserResult) -> {
                var loginUdpSender = new LoginUDPSender(udpClient);
                loginUdpSender.send(null, new LoginUserUDPRequest(adminId, uuid));
            }));

            loginTcpSender.send(null, new LoginUserRequest(adminId, uuid));
            System.out.println("User UUID: " + uuid);

            EventDispatcher.startListening(new GetProcessesReceiver());
            EventDispatcher.startListening(new GetHardwareInfoReceiver());
            EventDispatcher.startListening(new GetImageReceiver());

            EventDispatcher.startListening(new ProcessActionReceiver((data) -> {
                switch (data.action) {
                    case KILL -> {
                        try {
                            var process = Runtime.getRuntime().exec("taskkill /F /IM " + data.processId);

                            var inputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            var errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                            var isSuccess = inputStream.lines().findAny().isPresent();

                            var line = "";
                            var sender = new ProcessActionResultSender(tcpClient);
                            var result = isSuccess ? ProcessActionResultUserSide.ProcessActionResult.SUCCESS : ProcessActionResultUserSide.ProcessActionResult.FAILED;
                            var message = new StringBuilder();

                            if (isSuccess) {
                                while ((line = inputStream.readLine()) != null) {
                                    message.append(line).append("\n");
                                }
                            } else {
                                while ((line = errorStream.readLine()) != null) {
                                    message.append(line).append("\n");
                                }
                            }

                            sender.send(null, new ProcessActionResultUserSide(adminId, data.processId, data.action, message.toString(), result));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }));

            GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(false);
            keyboardHook.addKeyListener(new GlobalKeyListener() {
                @Override
                public void keyPressed(GlobalKeyEvent globalKeyEvent) {
//                    System.out.println(globalKeyEvent);
                }

                @Override
                public void keyReleased(GlobalKeyEvent globalKeyEvent) {

                }
            });

            Thread.currentThread().join();
        } catch (IOException e) {
            System.out.println("Application instance is already running.");
            System.exit(0);
        }

    }
}
