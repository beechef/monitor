package SocketMessageReceiver.DataType.GetImage;

import Server.MiddleWare.AdminRequest;

import java.io.Serializable;

public class GetImageRequestServerSide extends AdminRequest implements Serializable {

    public GetImageRequestServerSide(int adminId, String adminUuid) {
        super(adminId, adminUuid);
    }
}
