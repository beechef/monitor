package SocketMessageReceiver.DataType;

import Server.MiddleWare.TokenMark;

import java.io.Serializable;

public class ChangeUserNameRequest implements Serializable, TokenMark {
    public String token;

    public String userId;
    public String name;

    public ChangeUserNameRequest(String token, String userId, String name) {
        this.token = token;
        this.userId = userId;
        this.name = name;
    }

    @Override
    public String getToken() {
        return token;
    }
}
