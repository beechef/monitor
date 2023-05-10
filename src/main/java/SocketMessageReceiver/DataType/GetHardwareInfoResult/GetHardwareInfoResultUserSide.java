package SocketMessageReceiver.DataType.GetHardwareInfoResult;

import Server.Database.KeyPair;
import Server.MiddleWare.AdminRequest;
import SocketMessageReceiver.DataType.GetHardwareInfo.GetHardwareInfoAdminSide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GetHardwareInfoResultUserSide extends AdminRequest implements Serializable {
    public HashMap<GetHardwareInfoAdminSide.HardwareType, ArrayList<KeyPair<String, String>>> hardwareInfos;

    public GetHardwareInfoResultUserSide(int adminId, String adminUuid, HashMap<GetHardwareInfoAdminSide.HardwareType, ArrayList<KeyPair<String, String>>>  hardwareInfos) {
        super(adminId, adminUuid);
        this.hardwareInfos = hardwareInfos;
    }
}
