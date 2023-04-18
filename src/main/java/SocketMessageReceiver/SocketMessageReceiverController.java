package SocketMessageReceiver;

import Server.EventDispatcher.EventDispatcher;
import Server.EventDispatcher.SocketMessageEvent;
import SocketMessageReceiver.CustomServerReceiver.*;
import SocketMessageReceiver.CustomServerReceiver.GetHardwareInfo.GetHardwareInfoReceiver;
import SocketMessageReceiver.CustomServerReceiver.GetHardwareInfo.GetHardwareInfoResultReceiver;
import SocketMessageReceiver.CustomServerReceiver.GetProcesses.GetProcessesReceiver;
import SocketMessageReceiver.CustomServerReceiver.GetProcesses.GetProcessesResultReceiver;

import java.util.ArrayList;
import java.util.List;

public class SocketMessageReceiverController {
    private static final List<SocketMessageEvent> registeredTCPReceiver = new ArrayList<>();

    public static void registerEvents() {
        register(new LoginReceiver());
        register(new RegisterReceiver());
        register(new LoginUserReceiver());
        register(new GetUserReceiver());

        register(new GetHardwareInfoReceiver());
        register(new GetHardwareInfoResultReceiver());

        register(new GetProcessesReceiver());
        register(new GetProcessesResultReceiver());
    }

    private static void register(SocketMessageEvent receiver) {
        registeredTCPReceiver.add(receiver);
        EventDispatcher.startListening(receiver);
    }

    public static void unRegisterEvents() {
        for (var receiver : registeredTCPReceiver) {
            EventDispatcher.stopListening(receiver.getHeadByte(), receiver.getSubHeadByte(), receiver);
        }

        registeredTCPReceiver.clear();
    }
}
