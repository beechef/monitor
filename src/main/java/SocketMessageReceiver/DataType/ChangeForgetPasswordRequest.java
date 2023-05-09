package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class ChangeForgetPasswordRequest implements Serializable {
    public String email;
    public String otp;
    public String newPassword;

    public ChangeForgetPasswordRequest(String email, String otp, String newPassword) {
        this.email = email;
        this.otp = otp;
        this.newPassword = newPassword;
    }
}
