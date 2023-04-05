package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class RegisterResultRequest implements Serializable {
    public enum Result {
        SUCCESS,
        USERNAME_EXIST,
        EMAIL_EXIST,
    }

    public Result result;

    public RegisterResultRequest(Result result) {
        this.result = result;
    }
}
