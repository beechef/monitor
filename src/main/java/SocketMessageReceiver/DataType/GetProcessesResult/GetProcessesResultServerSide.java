package SocketMessageReceiver.DataType.GetProcessesResult;

import SocketMessageReceiver.DataType.ProcessInfo;

import java.io.Serializable;

public class GetProcessesResultServerSide implements Serializable {
    public ProcessInfo[] processes;

    public GetProcessesResultServerSide(ProcessInfo[] processes) {
        this.processes = processes;
    }
}
