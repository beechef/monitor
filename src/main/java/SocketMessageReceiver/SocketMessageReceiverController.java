package SocketMessageReceiver;

import Server.EventDispatcher.EventDispatcher;
import Server.ServerInstance.Server;
import SocketMessageReceiver.CustomServerReceiver.LoginReceiver;
import SocketMessageReceiver.CustomServerReceiver.RegisterReceiver;

import java.util.ArrayList;
import java.util.List;

public class SocketMessageReceiverController {
    private static final List<SocketMessageReceiver> registeredTCPReceiver = new ArrayList<>();

    public static void registerTCPAll(Server server) {
        register(new LoginReceiver(server));
        register(new RegisterReceiver(server));
    }

    private static void register(SocketMessageReceiver receiver) {
        registeredTCPReceiver.add(receiver);
        EventDispatcher.startListening(receiver.getHeadByte(), receiver.getSubHeadByte(), receiver);
    }

    public static void unRegisterTCPAll() {
        for (var receiver : registeredTCPReceiver) {
            EventDispatcher.stopListening(receiver.getHeadByte(), receiver.getSubHeadByte(), receiver);
        }

        registeredTCPReceiver.clear();
    }
}
