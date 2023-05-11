package SocketMessageReceiver.DataType.GetProcessesResult;

import Server.MiddleWare.AdminRequest;
import SocketMessageReceiver.DataType.ProcessInfo;

import java.io.Serializable;

public class GetProcessesResultUserSide extends AdminRequest implements Serializable {
    public String uuid;
    public ProcessInfo[] processes;

    public GetProcessesResultUserSide(int adminId, String adminUuid, String uuid, ProcessInfo[] processes) {
        super(adminId, adminUuid);
        this.uuid = uuid;
        this.processes = processes;
    }
}
