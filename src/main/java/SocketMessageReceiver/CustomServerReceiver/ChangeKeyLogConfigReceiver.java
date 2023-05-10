package SocketMessageReceiver.CustomServerReceiver;

import Server.Database.*;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.ChangeKeyLogConfigRequest;
import SocketMessageReceiver.DataType.ChangeKeyLogConfigResult;
import SocketMessageSender.CustomServerSender.ChangeKeyLogConfigSender;
import SocketMessageSender.CustomServerSender.ChangeKeyLogResultSender;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class ChangeKeyLogConfigReceiver extends AdminUserReceiver<ChangeKeyLogConfigRequest> {
    private static final String USER_TABLE = "user";
    private static final String USER_ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String USER_WRITE_LOG = "write_log";
    private static final String USER_WRITE_LOG_INTERVAL = "write_log_interval";


    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.CHANGE_KEY_LOG_CONFIG;
    }

    @Override
    protected void setData(Sender server, SocketMessageGeneric<ChangeKeyLogConfigRequest> socketMsg, AdminUserReceiver<ChangeKeyLogConfigRequest>.AdminUserInfo info) {
        var uuid = socketMsg.msg.userUuid;
        var isWriteLog = socketMsg.msg.isWriteLog;
        var writeLogInterval = socketMsg.msg.writeLogInterval;

        var userSender = new ChangeKeyLogConfigSender(server);
        userSender.send(info.userInfo.tcpSocket, new ChangeKeyLogConfigRequest(uuid, "", isWriteLog, writeLogInterval));
        update(uuid, isWriteLog, writeLogInterval);


        var admins = UserController.getAdmins(info.adminInfo.adminId);
        var adminSender = new ChangeKeyLogResultSender(server);

        for (var admin : admins) {
            adminSender.send(admin.tcpSocket, new ChangeKeyLogConfigResult(uuid, isWriteLog, writeLogInterval));
        }
    }

    private void update(String uuid, boolean isWriteLog, long writeLogInterval) {
        var conditions = new Condition[]{new Condition(USER_ID_FIELD, Operator.Equal, uuid, CombineCondition.NONE)};
        var values = new ArrayList<KeyPair<String, String>>();
        values.add(new KeyPair<>(USER_WRITE_LOG, isWriteLog ? "1" : "0"));
        values.add(new KeyPair<>(USER_WRITE_LOG_INTERVAL, String.valueOf(writeLogInterval)));

        try {
            DatabaseConnector.update(USER_TABLE, values, conditions);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
