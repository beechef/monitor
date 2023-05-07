package SocketMessageReceiver.DataType.GetHardwareInfo;

import Server.MiddleWare.AdminUserRequest;
import Server.MiddleWare.TokenMark;

import java.io.Serializable;
import java.util.ArrayList;

public class GetHardwareInfoAdminSide implements Serializable, AdminUserRequest {
    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getUserUuid() {
        return userUuid;
    }

    public enum HardwareType {
        CPU,
        MEMORY,
        DISK
    }

    public ArrayList<HardwareType> hardwareTypes;
    public String userUuid;
    public String token;

    public GetHardwareInfoAdminSide(ArrayList<HardwareType> hardwareTypes, String userUuid, String token) {
        this.token = token;
        this.userUuid = userUuid;
        this.hardwareTypes = hardwareTypes;
    }
}
