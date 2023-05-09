package SocketMessageReceiver.DataType.GetProcesses;

import Server.MiddleWare.AdminUserRequest;
import Server.MiddleWare.TokenMark;

import java.io.Serializable;

public class GetProcessesAdminSide implements Serializable, AdminUserRequest {
    public String token;
    public String userUuid;

    @Override
    public String getToken() {
        return token;
    }

    public GetProcessesAdminSide(String token, String userUuid) {
        this.token = token;
        this.userUuid = userUuid;
    }

    @Override
    public String getUserUuid() {
        return userUuid;
    }
}
