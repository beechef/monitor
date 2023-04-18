package SocketMessageReceiver.DataType.GetHardwareInfoResult;

import java.io.Serializable;
import java.util.ArrayList;

public class GetHardwareInfoResultUserSide implements Serializable {
    public int id;
    public String[] hardwareInfos;

    public GetHardwareInfoResultUserSide(int id, String[] hardwareInfos) {
        this.id = id;
        this.hardwareInfos = hardwareInfos;
    }
}
