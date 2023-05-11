package SocketMessageReceiver.DataType;

import Server.MiddleWare.TokenMark;

import java.io.Serializable;

public class LogOutAdminRequest implements TokenMark, Serializable {

    public String token;

    public LogOutAdminRequest(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
