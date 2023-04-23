package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class LoginUserResult implements Serializable {
    public GetUserResultRequest.UserInfo userInfo;

    public LoginUserResult(GetUserResultRequest.UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
