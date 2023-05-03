package SocketMessageReceiver.DataType;

import Server.MiddleWare.TokenMark;

import java.io.Serializable;

public class LoginAdminUdpRequest implements Serializable, TokenMark {
    public String token;

    public LoginAdminUdpRequest(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
