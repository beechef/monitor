package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class RegisterResultRequest implements Serializable {
    public enum Result {
        SUCCESS,
        USERNAME_EXIST,
        EMAIL_EXIST,
        UNKNOWN_ERROR,
        PASSWORD_LOWER_6_LENGTH,
        WRONG_FORMAT_EMAIL,
    }

    public Result result;

    public RegisterResultRequest(Result result) {
        this.result = result;
    }
}
