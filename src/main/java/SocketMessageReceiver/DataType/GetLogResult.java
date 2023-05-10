package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class GetLogResult implements Serializable {
    public String log;
    public boolean isEnd;

    public GetLogResult(String log, boolean isEnd) {
        this.log = log;
        this.isEnd = isEnd;
    }
}
