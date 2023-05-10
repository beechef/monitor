package SocketMessageReceiver.DataType;

import Server.MiddleWare.AdminUserRequest;

import java.io.Serializable;

public class ChangeKeyLogConfigRequest implements Serializable, AdminUserRequest {
    public String userUuid;
    public String token;

    public boolean isWriteLog;
    public long writeLogInterval;

    public ChangeKeyLogConfigRequest(String userUuid, String token, boolean isWriteLog, long writeLogInterval) {
        this.userUuid = userUuid;
        this.token = token;
        this.isWriteLog = isWriteLog;
        this.writeLogInterval = writeLogInterval;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getUserUuid() {
        return userUuid;
    }
}
