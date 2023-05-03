package SocketMessageReceiver.DataType.GetImage;

import Server.MiddleWare.AdminUserRequest;

import java.io.Serializable;

public class GetImageRequestAdminSide implements Serializable, AdminUserRequest {

    public String token;
    public String userUuid;

    public GetImageRequestAdminSide(String token, String userUuid) {
        this.token = token;
        this.userUuid = userUuid;
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
