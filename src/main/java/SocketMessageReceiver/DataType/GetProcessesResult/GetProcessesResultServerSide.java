package SocketMessageReceiver.DataType.GetProcessesResult;

import SocketMessageReceiver.DataType.ProcessInfo;

import java.io.Serializable;

public class GetProcessesResultServerSide implements Serializable {
    public String uuid;
    public ProcessInfo[] processes;

    public GetProcessesResultServerSide(String uuid, ProcessInfo[] processes) {
        this.uuid = uuid;
        this.processes = processes;
    }
}
