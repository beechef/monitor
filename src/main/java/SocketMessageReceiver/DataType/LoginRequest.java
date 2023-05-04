package SocketMessageReceiver.DataType;

import Server.MiddleWare.EncryptPasswordMark;

import java.io.Serializable;

public class LoginRequest implements Serializable, EncryptPasswordMark {
    public String uuid;
    public String email;
    public String password;

    public LoginRequest(String uuid, String email, String password) {
        this.uuid = uuid;
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
