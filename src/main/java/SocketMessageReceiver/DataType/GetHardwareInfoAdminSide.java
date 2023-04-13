package SocketMessageReceiver.DataType;

import Server.MiddleWare.TokenMark;

import java.io.Serializable;
import java.util.ArrayList;

public class GetHardwareInfoAdminSide implements Serializable, TokenMark {
    @Override
    public String getToken() {
        return token;
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
