package SocketMessageReceiver.DataType;

import Server.MiddleWare.EncryptPasswordMark;

import java.io.Serializable;

public class LoginRequest implements Serializable, EncryptPasswordMark {
    public String email;
    public String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
