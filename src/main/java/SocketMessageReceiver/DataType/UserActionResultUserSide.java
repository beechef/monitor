package SocketMessageReceiver.DataType;

import Server.MiddleWare.AdminRequest;

import java.io.Serializable;

public class UserActionResultUserSide extends AdminRequest implements Serializable {
    public enum Result {
        SUCCESS,
        FAILED,
    }

    public Result result;
    public UserActionRequestAdminSide.Action action;
    public String message;

    public UserActionResultUserSide(int adminId, String adminUuid, Result result, UserActionRequestAdminSide.Action action, String message) {
        super(adminId, adminUuid);
        this.result = result;
        this.action = action;
        this.message = message;
    }
}
