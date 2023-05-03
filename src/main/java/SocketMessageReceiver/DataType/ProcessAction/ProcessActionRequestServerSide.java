package SocketMessageReceiver.DataType.ProcessAction;

import java.io.Serializable;

public class ProcessActionRequestServerSide implements Serializable {
    public int processId;
    public ProcessActionRequestAdminSide.ProcessAction action;

    public int adminId;

    public ProcessActionRequestServerSide(int processId, ProcessActionRequestAdminSide.ProcessAction action, int adminId) {
        this.processId = processId;
        this.action = action;
        this.adminId = adminId;
    }
}
