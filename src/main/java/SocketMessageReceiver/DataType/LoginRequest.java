package SocketMessageReceiver.DataType;

import Server.MiddleWare.EncryptPasswordMark;

import java.io.Serializable;

public class LoginRequest implements Serializable, EncryptPasswordMark {
    public String username;
    public String password;

    public LoginRequest(String username, String password) {
        this.username = username;
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
