package SocketMessageReceiver.DataType.ProcessAction;

import Server.MiddleWare.AdminRequest;

import java.io.Serializable;

public class ProcessActionRequestServerSide extends AdminRequest implements Serializable {
    public int processId;
    public ProcessActionRequestAdminSide.ProcessAction action;

    public ProcessActionRequestServerSide(int adminId, String adminUuid, int processId, ProcessActionRequestAdminSide.ProcessAction action) {
        super(adminId, adminUuid);
        this.processId = processId;
        this.action = action;
    }
}
