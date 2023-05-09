package SocketMessageReceiver.DataType;

import Server.MiddleWare.TokenMark;

public class LogOutAdminRequest implements TokenMark {

    public String token;

    public LogOutAdminRequest(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
