package Client;

import Server.EventDispatcher.EventDispatcher;
import SocketMessageReceiver.CustomUserReceiver.GetHardwareInfoReceiver;
import SocketMessageReceiver.CustomUserReceiver.GetProcessesReceiver;
import SocketMessageReceiver.DataType.LoginUserRequest;
import SocketMessageSender.CustomUserSender.LoginSender;
import Utilities.Utilities;

import java.io.IOException;
import java.net.ServerSocket;

public class User {
    public static void main(String[] args) throws IOException, InterruptedException {
        try (ServerSocket ignored = new ServerSocket(9999)) {
            var client = new ClientTCP("localhost", 4445);
            client.setBuffer(1024 * 16);
            client.start();

            ClientInstance.tcpClient = client;

            var sender = new LoginSender(client);
            var uuid = Utilities.getUUID();
            var adminId = 11;

            sender.send(null, new LoginUserRequest(adminId, uuid));

            System.out.println(uuid);
            EventDispatcher.startListening(new GetProcessesReceiver());
            EventDispatcher.startListening(new GetHardwareInfoReceiver());

            Thread.currentThread().join();
        } catch (IOException e) {
            System.out.println("Application instance is already running.");
            System.exit(0);
        }

    }
}
