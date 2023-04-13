package SocketMessageReceiver.DataType;

import java.io.Serializable;
import java.util.ArrayList;

public class GetHardwareInfoResultUserSide implements Serializable {
    public int id;
    public ArrayList<String> hardwareInfos;

    public GetHardwareInfoResultUserSide(int id, ArrayList<String> hardwareInfos) {
        this.id = id;
        this.hardwareInfos = hardwareInfos;
    }
}
