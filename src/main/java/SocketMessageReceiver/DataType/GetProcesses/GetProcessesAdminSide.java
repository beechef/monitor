package SocketMessageReceiver.DataType.GetProcesses;

import Server.MiddleWare.TokenMark;

import java.io.Serializable;

public class GetProcessesAdminSide implements Serializable, TokenMark {
    public String token;
    public String userUuid;

    @Override
    public String getToken() {
        return token;
    }

    public GetProcessesAdminSide(String token, String userUuid) {
        this.token = token;
        this.userUuid = userUuid;
    }

}
