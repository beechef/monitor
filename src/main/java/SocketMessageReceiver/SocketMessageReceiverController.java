package SocketMessageReceiver;

import Server.EventDispatcher.EventDispatcher;
import SocketMessageReceiver.CustomServerReceiver.LoginReceiver;
import SocketMessageReceiver.CustomServerReceiver.RegisterReceiver;

public class SocketMessageReceiverController {
    public static void registerAll() {
        register(new LoginReceiver());
        register(new RegisterReceiver());
    }

    private static void register(SocketMessageReceiver receiver) {
        EventDispatcher.startListening(receiver.getHeadByte(), receiver.getSubHeadByte(), receiver);
    }
}
