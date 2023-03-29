package SocketMessageReceiver;

import Server.EventDispatcher.EventDispatcher;
import SocketMessageReceiver.CustomServerReceiver.LoginReceiver;
import SocketMessageReceiver.CustomServerReceiver.RegisterReceiver;

import java.util.ArrayList;
import java.util.List;

public class SocketMessageReceiverController {
    private static final List<SocketMessageReceiver> registeredReceiver = new ArrayList<>();

    public static void registerAll() {
        register(new LoginReceiver());
        register(new RegisterReceiver());
    }

    private static void register(SocketMessageReceiver receiver) {
        registeredReceiver.add(receiver);
        EventDispatcher.startListening(receiver.getHeadByte(), receiver.getSubHeadByte(), receiver);
    }

    public static void unRegisterAll() {
        for (var receiver : registeredReceiver) {
            EventDispatcher.stopListening(receiver.getHeadByte(), receiver.getSubHeadByte(), receiver);
        }

        registeredReceiver.clear();
    }
}
