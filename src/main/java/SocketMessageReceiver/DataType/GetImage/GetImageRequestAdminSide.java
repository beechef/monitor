package SocketMessageReceiver.DataType.GetImage;

import Server.MiddleWare.AdminUserRequest;

import java.io.Serializable;

public class GetImageRequestAdminSide implements Serializable, AdminUserRequest {

    public String token;
    public String userUuid;


    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getUserUuid() {
        return userUuid;
    }
}
