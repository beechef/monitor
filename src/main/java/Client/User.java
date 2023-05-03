package Client;

import Server.EventDispatcher.EventDispatcher;
import Server.ServerInstance.Message;
import SocketMessageReceiver.CustomAdminReceiver.LoginUserResultReceiver;
import SocketMessageReceiver.CustomUserReceiver.GetHardwareInfoReceiver;
import SocketMessageReceiver.CustomUserReceiver.GetImageReceiver;
import SocketMessageReceiver.CustomUserReceiver.GetProcessesReceiver;
import SocketMessageReceiver.DataType.LoginUserRequest;
import SocketMessageReceiver.DataType.LoginUserUDPRequest;
import SocketMessageSender.CustomUserSender.LoginSender;
import SocketMessageSender.CustomUserSender.LoginUDPSender;
import Utilities.Utilities;

import java.io.IOException;
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


            Thread.currentThread().join();
        } catch (IOException e) {
            System.out.println("Application instance is already running.");
            System.exit(0);
        }

    }
}
