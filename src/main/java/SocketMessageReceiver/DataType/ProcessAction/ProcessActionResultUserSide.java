package SocketMessageReceiver.DataType.ProcessAction;

import Server.MiddleWare.AdminRequest;

import java.io.Serializable;

public class ProcessActionResultUserSide extends AdminRequest implements Serializable {
    public enum ProcessActionResult {
        SUCCESS,
        FAILED,
    }

    public String uuid;
    public int processId;
    public ProcessActionRequestAdminSide.ProcessAction action;
    public String message;
    public ProcessActionResult result;

    public ProcessActionResultUserSide(int adminId, String adminUuid, String uuid, int processId, ProcessActionRequestAdminSide.ProcessAction action, String message, ProcessActionResult result) {
        super(adminId, adminUuid);
        this.uuid = uuid;
        this.processId = processId;
        this.action = action;
        this.message = message;
        this.result = result;
    }
}
