package SocketMessageReceiver.DataType.GetHardwareInfoResult;

import Server.MiddleWare.AdminRequest;

import java.io.Serializable;

public class GetHardwareInfoResultUserSide extends AdminRequest implements Serializable {
    public String[] hardwareInfos;

    public GetHardwareInfoResultUserSide(int adminId, String adminUuid, String[] hardwareInfos) {
        super(adminId, adminUuid);
        this.hardwareInfos = hardwareInfos;
    }
}
