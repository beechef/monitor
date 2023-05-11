package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class GetLogResult implements Serializable {
    public String uuid;
    public String log;
    public boolean isEnd;

    public GetLogResult(String uuid, String log, boolean isEnd) {
        this.uuid = uuid;
        this.log = log;
        this.isEnd = isEnd;
    }
}
