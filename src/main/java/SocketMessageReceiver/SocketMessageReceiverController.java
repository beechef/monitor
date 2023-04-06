package SocketMessageReceiver;

import Server.EventDispatcher.EventDispatcher;
import Server.EventDispatcher.SocketMessageEvent;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.CustomServerReceiver.LoginReceiver;
import SocketMessageReceiver.CustomServerReceiver.RegisterReceiver;

import java.util.ArrayList;
import java.util.List;

public class SocketMessageReceiverController {
    private static final List<SocketMessageEvent> registeredTCPReceiver = new ArrayList<>();

    public static void registerTCPAll(Sender server) {
        register(new LoginReceiver());
        register(new RegisterReceiver());
    }

    private static void register(SocketMessageEvent receiver) {
        registeredTCPReceiver.add(receiver);
        EventDispatcher.startListening(receiver);
    }

    public static void unRegisterTCPAll() {
        for (var receiver : registeredTCPReceiver) {
            EventDispatcher.stopListening(receiver.getHeadByte(), receiver.getSubHeadByte(), receiver);
        }

        registeredTCPReceiver.clear();
    }
}
