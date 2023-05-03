package SocketMessageReceiver.DataType.GetImage;

import java.io.Serializable;

public class GetImageResultServerSide implements Serializable {
    public byte[] image;
    public boolean isEnd;
    public String session;

    public GetImageResultServerSide(byte[] image, String session, boolean isEnd) {
        this.image = image;
        this.session = session;
        this.isEnd = isEnd;
    }
}
