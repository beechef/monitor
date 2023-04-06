package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class LoginResultRequest implements Serializable {
    public enum Result {
        SUCCESS,
        EMAIL_NOT_EXIST,
        PASSWORD_WRONG,
        UNKNOWN_ERROR,
    }

    public Result result;

    public LoginResultRequest(Result result) {
        this.result = result;
    }
}
