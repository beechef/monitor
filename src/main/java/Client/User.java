package Client;

import Server.EventDispatcher.EventDispatcher;
import SocketMessageReceiver.CustomUserReceiver.GetHardwareInfoReceiver;
import SocketMessageReceiver.DataType.LoginUserRequest;
import SocketMessageSender.CustomUserSender.LoginSender;
import Utilities.Utilities;

import java.io.IOException;

public class User {
    public static void main(String[] args) throws IOException, InterruptedException {
        var client = new ClientTCP("localhost", 4445);
        client.start();

        ClientInstance.tcpClient = client;

        var sender = new LoginSender(client);
        var uuid = Utilities.getUUID();
        var adminId = 5;

        sender.send(null, new LoginUserRequest(adminId, uuid));

        System.out.println(uuid);
        EventDispatcher.startListening(new GetHardwareInfoReceiver());

        Thread.currentThread().join();
    }
}
