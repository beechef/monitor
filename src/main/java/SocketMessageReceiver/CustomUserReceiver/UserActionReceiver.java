package SocketMessageReceiver.CustomUserReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.UserActionRequestServerSide;
import SocketMessageReceiver.DataType.UserActionResultUserSide;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomUserSender.UserActionResultSender;
import Utilities.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserActionReceiver extends SocketMessageReceiver<UserActionRequestServerSide> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserConnection.ACTION;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<UserActionRequestServerSide> socketMsg) {
        var action = socketMsg.msg.action;
        var uuid = Utilities.getUUID();
        var adminId = socketMsg.msg.adminId;
        var adminUuid = socketMsg.msg.adminUuid;

        var sender = new UserActionResultSender(server);
        CommandResponse response = null;
        switch (action) {
            case LOG_OUT -> {
                try {
                    response = executeCommand("shutdown -l");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case SHUTDOWN -> {
                try {
                    response = executeCommand("shutdown -s -f -t 0");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (response == null) return;
        sender.send(null, new UserActionResultUserSide(adminId, adminUuid, uuid,
                response.isSuccess ? UserActionResultUserSide.Result.SUCCESS : UserActionResultUserSide.Result.FAILED,
                action, response.message));
    }

    private CommandResponse executeCommand(String cmd) throws IOException {
        var process = Runtime.getRuntime().exec(cmd);

        var inputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
        var errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        var isSuccess = inputStream.lines().findAny().isPresent();

        var line = "";
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

        return new CommandResponse(isSuccess, message.toString());
    }

    private class CommandResponse {
        public boolean isSuccess;
        public String message;

        public CommandResponse(boolean isSuccess, String message) {
            this.isSuccess = isSuccess;
            this.message = message;
        }
    }
}
