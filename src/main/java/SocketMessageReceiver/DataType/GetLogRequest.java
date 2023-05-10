package SocketMessageReceiver.DataType;

import Server.MiddleWare.AdminUserRequest;

import java.io.Serializable;
import java.util.Date;

public class GetLogRequest implements AdminUserRequest, Serializable {
    public String userUuid;
    public String token;

    public Date from;
    public Date to;

    public GetLogRequest(String userUuid, String token, Date from, Date to) {
        this.userUuid = userUuid;
        this.token = token;
        this.from = from;
        this.to = to;
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
