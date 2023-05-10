package SocketMessageReceiver.DataType;

import Server.MiddleWare.AdminRequest;

import java.io.Serializable;

public class UserActionRequestServerSide extends AdminRequest implements Serializable {
    public UserActionRequestAdminSide.Action action;

    public UserActionRequestServerSide(int adminId, String adminUuid, UserActionRequestAdminSide.Action action) {
        super(adminId, adminUuid);
        this.action = action;
    }
}
