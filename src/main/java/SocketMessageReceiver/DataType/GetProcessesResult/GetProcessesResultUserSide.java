package SocketMessageReceiver.DataType.GetProcessesResult;

import Server.MiddleWare.AdminRequest;
import SocketMessageReceiver.DataType.ProcessInfo;

import java.io.Serializable;

public class GetProcessesResultUserSide extends AdminRequest implements Serializable {
    public ProcessInfo[] processes;

    public GetProcessesResultUserSide(int adminId, String adminUuid, ProcessInfo[] processes) {
        super(adminId, adminUuid);
        this.processes = processes;
    }
}
