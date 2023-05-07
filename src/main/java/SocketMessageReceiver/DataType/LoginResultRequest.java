package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class LoginResultRequest implements Serializable {
    public enum Result {
        SUCCESS,
        EMAIL_NOT_EXIST,
        PASSWORD_WRONG,
        UNKNOWN_ERROR,
        ALREADY_LOGIN
    }

    public Result result;
    public String token;

    public LoginResultRequest(Result result, String token) {
        this.result = result;
        this.token = token;
    }
}
