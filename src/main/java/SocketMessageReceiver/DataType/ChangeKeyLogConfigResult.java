package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class ChangeKeyLogConfigResult implements Serializable {
    public String uuid;
    public boolean isWriteLog;
    public long writeLogInterval;

    public ChangeKeyLogConfigResult(String uuid, boolean isWriteLog, long writeLogInterval) {
        this.uuid = uuid;
        this.isWriteLog = isWriteLog;
        this.writeLogInterval = writeLogInterval;
    }
}
