package SocketMessageReceiver.DataType.GetImage;

import java.io.Serializable;

public class GetImageResultUserSide implements Serializable {
    public int id;
    public byte[] image;
    public String session;
    public boolean isEnd;

    public GetImageResultUserSide(int id, byte[] image, String session,boolean isEnd) {
        this.id = id;
        this.image = image;
        this.session = session;
        this.isEnd = isEnd;
    }
}
