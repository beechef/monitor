package SocketMessageReceiver.DataType.GetHardwareInfoResult;

import java.io.Serializable;

public class GetHardwareInfoResultServerSide implements Serializable {
    public String[] hardwareInfos;

    public GetHardwareInfoResultServerSide(String[] hardwareInfos) {
        this.hardwareInfos = hardwareInfos;
    }
}
