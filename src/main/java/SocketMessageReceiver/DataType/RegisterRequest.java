package SocketMessageReceiver.DataType;

import Server.MiddleWare.EncryptPasswordMark;

import java.io.Serializable;

public class RegisterRequest implements Serializable, EncryptPasswordMark {
    public String username;
    public String password;
    public String email;
    public String phoneNumber;

    public RegisterRequest(String username, String password, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public RegisterRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
