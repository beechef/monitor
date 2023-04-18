package SocketMessageReceiver.DataType.GetProcessesResult;

import SocketMessageReceiver.DataType.ProcessInfo;

import java.io.Serializable;

public class GetProcessesResultUserSide implements Serializable {
    public int id;
    public ProcessInfo[] processes;

    public GetProcessesResultUserSide(int id, ProcessInfo[] processes) {
        this.id = id;
        this.processes = processes;
    }
}
