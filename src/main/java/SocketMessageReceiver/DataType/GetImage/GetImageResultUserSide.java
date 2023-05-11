package SocketMessageReceiver.DataType.GetImage;

import Server.MiddleWare.AdminRequest;

import java.io.Serializable;

public class GetImageResultUserSide extends AdminRequest implements Serializable {
    public byte[] image;
    public boolean isEnd;

    public String uuid;

    public GetImageResultUserSide(int adminId, String adminUuid, byte[] image, boolean isEnd, String uuid) {
        super(adminId, adminUuid);
        this.image = image;
        this.isEnd = isEnd;
        this.uuid = uuid;
    }
}
