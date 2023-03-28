package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class RegisterRequest implements Serializable {
    public String username;
    public String password;
    public String email;

    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
