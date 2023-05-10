package SocketMessageReceiver.DataType;

import Server.MiddleWare.AdminUserRequest;

import java.io.Serializable;

public class UserActionRequestAdminSide implements AdminUserRequest, Serializable {
    public enum Action {
        SHUTDOWN,
        LOG_OUT,
    }

    public String userUuid;
    public String token;

    public Action action;

    public UserActionRequestAdminSide(String userUuid, String token, Action action) {
        this.userUuid = userUuid;
        this.token = token;
        this.action = action;
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
