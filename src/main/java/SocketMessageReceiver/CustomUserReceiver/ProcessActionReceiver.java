package SocketMessageReceiver.CustomUserReceiver;

import Client.ClientInstance;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.ExecutableData;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.ProcessAction.ProcessActionRequestServerSide;
import SocketMessageReceiver.DataType.ProcessAction.ProcessActionResultUserSide;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageReceiver.SocketMessageReceiverCallBack;
import SocketMessageSender.CustomUserSender.ProcessActionResultSender;
import Utilities.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessActionReceiver extends SocketMessageReceiver<ProcessActionRequestServerSide> {

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.PROCESS_ACTION;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<ProcessActionRequestServerSide> socketMsg) {
        var data = socketMsg.msg;
        var uuid = Utilities.getUUID();
        var adminId = data.adminId;
        var adminUuid = data.adminUuid;

        switch (socketMsg.msg.action) {
            case KILL -> {
                try {
                    var process = Runtime.getRuntime().exec("taskkill /F /IM " + data.processId);

                    var inputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    var errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    var isSuccess = inputStream.lines().findAny().isPresent();

                    var line = "";
                    var sender = new ProcessActionResultSender(ClientInstance.tcpClient);
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

                    sender.send(null, new ProcessActionResultUserSide(adminId, adminUuid, uuid, data.processId, data.action, message.toString(), result));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
