package SocketMessageReceiver.DataType.ProcessAction;

import Server.MiddleWare.AdminUserRequest;

import java.io.Serializable;

public class ProcessActionRequestAdminSide implements Serializable, AdminUserRequest {
    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getUserUuid() {
        return userUuid;
    }

    public enum ProcessAction {
        KILL,
    }

    public int processId;
    public ProcessAction action;
    public String token;
    public String userUuid;

    public ProcessActionRequestAdminSide(int processId, ProcessAction action, String token, String userUuid) {
        this.processId = processId;
        this.action = action;
        this.token = token;
        this.userUuid = userUuid;
    }
}
