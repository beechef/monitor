package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class LoginUserRequest implements Serializable {
    public int adminId;
    public String deviceId;

    public LoginUserRequest(int adminId, String deviceId) {
        this.adminId = adminId;
        this.deviceId = deviceId;
    }
}
