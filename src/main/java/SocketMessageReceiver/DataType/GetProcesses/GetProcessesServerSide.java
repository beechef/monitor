package SocketMessageReceiver.DataType.GetProcesses;

import Server.MiddleWare.AdminRequest;

import java.io.Serializable;

public class GetProcessesServerSide extends AdminRequest implements Serializable {

    public GetProcessesServerSide(int adminId, String adminUuid) {
        super(adminId, adminUuid);
    }
}
