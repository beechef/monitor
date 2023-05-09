package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class LoginUserResultUserSide implements Serializable {
    public enum LoginResult {
        SUCCESS,
        NOT_FOUND_ADMIN,
    }

    public LoginResult loginResult;

    public LoginUserResultUserSide(LoginResult loginResult) {
        this.loginResult = loginResult;
    }
}
