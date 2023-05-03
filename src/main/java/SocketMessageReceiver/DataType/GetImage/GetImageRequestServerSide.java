package SocketMessageReceiver.DataType.GetImage;

import java.io.Serializable;

public class GetImageRequestServerSide implements Serializable {
    public int id;

    public GetImageRequestServerSide(int id) {
        this.id = id;
    }
}
