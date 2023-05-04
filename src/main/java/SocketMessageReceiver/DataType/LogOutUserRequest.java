package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class LogOutUserRequest implements Serializable {
    public int adminId;
    public String deviceId;

    public LogOutUserRequest(int adminId, String deviceId) {
        this.adminId = adminId;
        this.deviceId = deviceId;
    }
}
