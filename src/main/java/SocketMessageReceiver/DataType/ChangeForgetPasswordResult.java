package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class ChangeForgetPasswordResult implements Serializable {
    public enum Result {
        SUCCESS,
        WRONG_OTP,
    }

    public Result result;

    public ChangeForgetPasswordResult(Result result) {
        this.result = result;
    }
}
