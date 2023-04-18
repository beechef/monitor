package SocketMessageReceiver.DataType.GetHardwareInfo;

import java.io.Serializable;
import java.util.ArrayList;

public class GetHardwareInfoServerSide implements Serializable {

    public ArrayList<GetHardwareInfoAdminSide.HardwareType> hardwareTypes;
    public int id;

    public GetHardwareInfoServerSide(ArrayList<GetHardwareInfoAdminSide.HardwareType> hardwareTypes, int id) {
        this.id = id;
        this.hardwareTypes = hardwareTypes;
    }
}
