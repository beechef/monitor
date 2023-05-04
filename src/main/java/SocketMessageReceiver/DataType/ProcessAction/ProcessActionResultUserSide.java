package SocketMessageReceiver.DataType.ProcessAction;

import java.io.Serializable;

public class ProcessActionResultUserSide implements Serializable {
    public enum ProcessActionResult {
        SUCCESS,
        FAILED,
    }

    public int adminId;
    public int processId;
    public ProcessActionRequestAdminSide.ProcessAction action;
    public String message;
    public ProcessActionResult result;

    public ProcessActionResultUserSide(int adminId, int processId, ProcessActionRequestAdminSide.ProcessAction action, String message, ProcessActionResult result) {
        this.adminId = adminId;
        this.processId = processId;
        this.action = action;
        this.message = message;
        this.result = result;
    }
}
