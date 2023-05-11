package SocketMessageReceiver.DataType.GetImage;

import Server.MiddleWare.AdminUserRequest;

import java.io.Serializable;

public class GetImageRequestAdminSide implements Serializable, AdminUserRequest {

    public String token;
    public String userUuid;

    public GetImageRequestAdminSide(String tokenAdmin, String id) {
        this.token=tokenAdmin;
        this.userUuid=id;
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
