package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class ForgetPasswordRequest implements Serializable {
    public String email;

    public ForgetPasswordRequest(String email) {
        this.email = email;
    }
}
