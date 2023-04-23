package SocketMessageReceiver.DataType;

import java.io.Serializable;

public class ChangeUserNameResult implements Serializable {
    public String uuid;
    public String beforeName;
    public String afterName;

    public ChangeUserNameResult(String uuid, String beforeName, String afterName) {
        this.uuid = uuid;
        this.beforeName = beforeName;
        this.afterName = afterName;
    }
}
