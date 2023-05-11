package SocketMessageReceiver.DataType;

import Server.MiddleWare.AdminRequest;

import java.io.Serializable;

public class UserActionResultUserSide extends AdminRequest implements Serializable {
    public enum Result {
        SUCCESS,
        FAILED,
    }

    public String uuid;
    public Result result;
    public UserActionRequestAdminSide.Action action;
    public String message;

    public UserActionResultUserSide(int adminId, String adminUuid, String uuid, Result result, UserActionRequestAdminSide.Action action, String message) {
        super(adminId, adminUuid);
        this.uuid = uuid;
        this.result = result;
        this.action = action;
        this.message = message;
    }
}
