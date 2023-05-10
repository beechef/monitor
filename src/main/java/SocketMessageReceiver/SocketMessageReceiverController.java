package SocketMessageReceiver;

import Server.EventDispatcher.EventDispatcher;
import Server.EventDispatcher.SocketMessageEvent;
import SocketMessageReceiver.CustomServerReceiver.*;
import SocketMessageReceiver.CustomServerReceiver.GetHardwareInfo.GetHardwareInfoReceiver;
import SocketMessageReceiver.CustomServerReceiver.GetHardwareInfo.GetHardwareInfoResultReceiver;
import SocketMessageReceiver.CustomServerReceiver.GetImage.GetImageReceiver;
import SocketMessageReceiver.CustomServerReceiver.GetImage.GetImageResultReceiver;
import SocketMessageReceiver.CustomServerReceiver.GetProcesses.GetProcessesReceiver;
import SocketMessageReceiver.CustomServerReceiver.GetProcesses.GetProcessesResultReceiver;
import SocketMessageReceiver.CustomServerReceiver.ProcessAction.ProcessActionReceiver;
import SocketMessageReceiver.CustomServerReceiver.ProcessAction.ProcessActionResultReceiver;

import java.util.ArrayList;
import java.util.List;

public class SocketMessageReceiverController {
    private static final List<SocketMessageEvent> registeredTCPReceiver = new ArrayList<>();

    public static void registerEvents() {
        register(new LoginReceiver());
        register(new RegisterReceiver());
        register(new LoginUserReceiver());
        register(new LoginUserUDPReceiver());
        register(new LoginAdminUdpReceiver());
        register(new GetUserReceiver());

        register(new GetHardwareInfoReceiver());
        register(new GetHardwareInfoResultReceiver());

        register(new GetProcessesReceiver());
        register(new GetProcessesResultReceiver());

        register(new ChangeUserNameReceiver());

        register(new GetImageReceiver());
        register(new GetImageResultReceiver());

        register(new ProcessActionReceiver());
        register(new ProcessActionResultReceiver());

        register(new LogOutUserReceiver());
        register(new LogOutAdminReceiver());

        register(new ForgetPasswordReceiver());
        register(new ChangeForgetPasswordReceiver());

        register(new KeyLogReceiver());

        register(new ChangeKeyLogConfigReceiver());
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
