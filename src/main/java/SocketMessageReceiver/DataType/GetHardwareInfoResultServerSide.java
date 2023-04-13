package SocketMessageReceiver.DataType;

import java.io.Serializable;
import java.util.ArrayList;

public class GetHardwareInfoResultServerSide implements Serializable {
    public ArrayList<String> hardwareInfos;

    public GetHardwareInfoResultServerSide(ArrayList<String> hardwareInfos) {
        this.hardwareInfos = hardwareInfos;
    }
}
