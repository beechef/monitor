package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class UserActionResultServerSide implements Serializable {
    public String uuid;
    public UserActionRequestAdminSide.Action action;
    public UserActionResultUserSide.Result result;
    public String message;

    public UserActionResultServerSide(String uuid, UserActionRequestAdminSide.Action action, UserActionResultUserSide.Result result, String message) {
        this.uuid = uuid;
        this.action = action;
        this.result = result;
        this.message = message;
    }
}
