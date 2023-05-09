package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class LoginUserResultAdminSide implements Serializable {
    public GetUserResultRequest.UserInfo userInfo;

    public LoginUserResultAdminSide(GetUserResultRequest.UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
