package SocketMessageReceiver.DataType.GetImage;

import java.io.Serializable;

public class GetImageResultServerSide implements Serializable {
    public byte[] image;
    public boolean isEnd;

    public GetImageResultServerSide(byte[] image, boolean isEnd) {
        this.image = image;
        this.isEnd = isEnd;
    }
}
