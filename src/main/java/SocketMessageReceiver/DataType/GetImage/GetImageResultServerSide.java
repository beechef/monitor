package SocketMessageReceiver.DataType.GetImage;

import java.io.Serializable;

public class GetImageResultServerSide implements Serializable {
    public byte[] image;
    public boolean isEnd;
    public String uuid;

    public GetImageResultServerSide(byte[] image, boolean isEnd, String uuid) {
        this.image = image;
        this.isEnd = isEnd;
        this.uuid = uuid;
    }
}
