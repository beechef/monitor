package SocketMessageReceiver.DataType;

import Server.MiddleWare.TokenMark;

import java.io.Serializable;

public class GetUserRequest implements Serializable, TokenMark {
    @Override
    public String getToken() {
        return token;
    }

    public enum Type {
        GET_ALL,
    }

    public Type type;
    public String token;

    public GetUserRequest(Type type, String token) {
        this.type = type;
        this.token = token;
    }
}
