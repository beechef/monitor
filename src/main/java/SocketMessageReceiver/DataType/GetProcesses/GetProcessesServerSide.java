package SocketMessageReceiver.DataType.GetProcesses;

import java.io.Serializable;

public class GetProcessesServerSide implements Serializable {
    public int id;

    public GetProcessesServerSide(int id) {
        this.id = id;
    }
}
