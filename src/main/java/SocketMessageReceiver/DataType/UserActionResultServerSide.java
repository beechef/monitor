package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class UserActionResultServerSide implements Serializable {
    public UserActionRequestAdminSide.Action action;
    public UserActionResultUserSide.Result result;
    public String message;

    public UserActionResultServerSide(UserActionRequestAdminSide.Action action, UserActionResultUserSide.Result result, String message) {
        this.action = action;
        this.result = result;
        this.message = message;
    }
}
