package SocketMessageReceiver.DataType.GetHardwareInfoResult;

import Server.Database.KeyPair;
import SocketMessageReceiver.DataType.GetHardwareInfo.GetHardwareInfoAdminSide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GetHardwareInfoResultServerSide implements Serializable {
    public HashMap<GetHardwareInfoAdminSide.HardwareType, ArrayList<KeyPair<String, String>>> hardwareInfos;

    public GetHardwareInfoResultServerSide(HashMap<GetHardwareInfoAdminSide.HardwareType, ArrayList<KeyPair<String, String>>> hardwareInfos) {
        this.hardwareInfos = hardwareInfos;
    }
}
