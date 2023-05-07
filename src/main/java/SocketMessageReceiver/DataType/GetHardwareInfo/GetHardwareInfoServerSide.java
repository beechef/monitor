package SocketMessageReceiver.DataType.GetHardwareInfo;

import Server.MiddleWare.AdminRequest;

import java.io.Serializable;
import java.util.ArrayList;

public class GetHardwareInfoServerSide extends AdminRequest implements Serializable {

    public ArrayList<GetHardwareInfoAdminSide.HardwareType> hardwareTypes;

    public GetHardwareInfoServerSide(int adminId, String adminUuid, ArrayList<GetHardwareInfoAdminSide.HardwareType> hardwareTypes) {
        super(adminId, adminUuid);
        this.hardwareTypes = hardwareTypes;
    }
}
