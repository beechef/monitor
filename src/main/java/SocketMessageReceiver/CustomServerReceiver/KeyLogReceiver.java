package SocketMessageReceiver.CustomServerReceiver;

import Server.Database.*;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.KeyLogRequest;
import SocketMessageReceiver.SocketMessageReceiver;

import java.sql.SQLException;
import java.util.ArrayList;

public class KeyLogReceiver extends SocketMessageReceiver<KeyLogRequest> {
    private static final String USER_LOG_TABLE = "user_log";
    private static final String ID = "id_user";
    private static final String LOG = "log";
    private static final String DATE = "date";

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.SEND_KEY_LOG;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<KeyLogRequest> socketMsg) {
        insertLog(socketMsg.msg.uuid, socketMsg.msg.log, String.valueOf(socketMsg.msg.date));
    }

    private void insertLog(String uuid, String log, String date) {
        var values = new ArrayList<KeyPair<String, String>>();
        values.add(new KeyPair<>(ID, uuid));
        values.add(new KeyPair<>(LOG, log));
        values.add(new KeyPair<>(DATE, date));

        try {
            DatabaseConnector.insert(USER_LOG_TABLE, values);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
