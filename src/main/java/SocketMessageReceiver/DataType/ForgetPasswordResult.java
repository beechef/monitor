package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class ForgetPasswordResult implements Serializable {
    public enum Result {
        SUCCESS,
        EMAIL_NOT_FOUND,
    }

    public String email;

    public Result result;

    public ForgetPasswordResult(String email, Result result) {
        this.email = email;
//        this.otp = otp;
        this.result = result;
    }
}
