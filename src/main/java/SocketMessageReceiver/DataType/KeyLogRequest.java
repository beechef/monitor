package SocketMessageReceiver.DataType;

import java.io.Serializable;
import java.sql.Date;

public class KeyLogRequest implements Serializable {
    public String uuid;
    public String log;
    public String date;

    public KeyLogRequest(String uuid, String log, String date) {
        this.uuid = uuid;
        this.log = log;
        this.date = date;
    }
}
