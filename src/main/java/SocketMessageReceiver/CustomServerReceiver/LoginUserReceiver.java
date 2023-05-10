package SocketMessageReceiver.CustomServerReceiver;

import Server.Database.*;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.GetUserResultRequest;
import SocketMessageReceiver.DataType.LoginUserRequest;
import SocketMessageReceiver.DataType.LoginUserResultAdminSide;
import SocketMessageReceiver.DataType.LoginUserResultUserSide;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.LoginUserResultAdminSideSender;
import SocketMessageSender.CustomServerSender.LoginUserResultUserSideSender;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginUserReceiver extends SocketMessageReceiver<LoginUserRequest> {
    private static final String USER_TABLE = "user";
    private static final String USER_ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String USER_WRITE_LOG = "write_log";
    private static final String USER_WRITE_LOG_INTERVAL = "write_log_interval";

    private static final String BINDING_TABLE = "binding";
    private static final String BINDING_ADMIN_ID_FIELD = "id_admin";
    private static final String BINDING_USER_ID_FIELD = "id_user";

    private static final String ADMIN_TABLE = "admin";
    private static final String ADMIN_ID_FIELD = "id";

    private static final boolean DEFAULT_WRITE_LOG = true;
    private static final long DEFAULT_WRITE_LOG_INTERVAL = 30L * 60; // 30 minutes


    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserConnection.LOGIN;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<LoginUserRequest> socketMsg) {
        var user = socketMsg.sender;
        var adminId = socketMsg.msg.adminId;
        var deviceId = socketMsg.msg.deviceId;

        try {
            var existAdmin = getAdmin(adminId);
            var userSender = new LoginUserResultUserSideSender(server);
            var isWriteLog = DEFAULT_WRITE_LOG;
            var writeLogInterval = DEFAULT_WRITE_LOG_INTERVAL;

            if (!existAdmin.next()) {
                userSender.send(user, new LoginUserResultUserSide(LoginUserResultUserSide.LoginResult.NOT_FOUND_ADMIN));
                return;
            }

            InetSocketAddress inetAddress = getInitAddress(user);
            var existUser = getUser(deviceId);
            var name = deviceId;

            if (existUser.next()) {
                name = existUser.getString(NAME_FIELD);
                isWriteLog = existUser.getBoolean(USER_WRITE_LOG);
                writeLogInterval = existUser.getLong(USER_WRITE_LOG_INTERVAL);
            } else {
                registerUser(deviceId, name, isWriteLog, writeLogInterval);
            }

            bindingAdminAndUser(adminId, deviceId);

            var userInfo = new UserController.UserInfo(deviceId, name, UserController.UserInfo.UserStatus.AVAILABLE, isWriteLog, writeLogInterval, inetAddress, user);
            UserController.addUser(adminId, userInfo);

            userSender.send(user, new LoginUserResultUserSide(LoginUserResultUserSide.LoginResult.SUCCESS));

            var adminSender = new LoginUserResultAdminSideSender(server);
            var admins = UserController.getAdmins(adminId);

            for (var admin : admins) {
                adminSender.send(admin.tcpSocket, new LoginUserResultAdminSide(new GetUserResultRequest.UserInfo(userInfo)));
            }

            adminSender.send(userInfo.tcpSocket, new LoginUserResultAdminSide(new GetUserResultRequest.UserInfo(userInfo)));

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private InetSocketAddress getInitAddress(Object user) throws IOException {
        if (user instanceof AsynchronousSocketChannel socketChannel) {
            var params = socketChannel.getRemoteAddress().toString();
            var address = params.substring(1, params.indexOf(":"));
            var port = Integer.parseInt(params.substring(params.indexOf(":") + 1));

            return new InetSocketAddress(address, port);
        }

        return null;
    }

    private ResultSet getData(String id, String tableName, String idField) {
        var conditions = new Condition[]{new Condition(idField, Operator.Equal, id, CombineCondition.NONE)};
        try {
            return DatabaseConnector.select(tableName, null, conditions);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ResultSet getUser(String id) {
        return getData(id, USER_TABLE, USER_ID_FIELD);
    }

    private ResultSet getAdmin(int id) {
        return getData(String.valueOf(id), ADMIN_TABLE, ADMIN_ID_FIELD);
    }

    private void registerUser(String id, String name, boolean isWriteLog, long writeLogInterval) {
        try {
            var values = new String[]{USER_ID_FIELD, NAME_FIELD, USER_WRITE_LOG, USER_WRITE_LOG_INTERVAL};
            var data = new String[]{id, name, isWriteLog ? "1" : "0", String.valueOf(writeLogInterval)};
            var keyPairs = new ArrayList<KeyPair<String, String>>(values.length);

            for (int i = 0; i < values.length; i++) {
                keyPairs.add(new KeyPair<>(values[i], data[i]));
            }

            DatabaseConnector.insert(USER_TABLE, keyPairs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void bindingAdminAndUser(int adminId, String userId) {
        try {
            var existBinding = getData(userId, BINDING_TABLE, BINDING_USER_ID_FIELD);
            var isExist = existBinding.next();

            var values = new String[]{BINDING_ADMIN_ID_FIELD, BINDING_USER_ID_FIELD};
            var data = new String[]{String.valueOf(adminId), userId};

            if (isExist) {
                var isSameAdmin = existBinding.getInt(BINDING_ADMIN_ID_FIELD) == adminId;

                if (isSameAdmin) {
                    return;
                }

                var conditions = new Condition[]{new Condition(BINDING_USER_ID_FIELD, Operator.Equal, userId, CombineCondition.NONE)};
                DatabaseConnector.delete(BINDING_TABLE, conditions);
            }

            var keyPairs = new ArrayList<KeyPair<String, String>>(values.length);

            for (int i = 0; i < values.length; i++) {
                keyPairs.add(new KeyPair<>(values[i], data[i]));
            }

            DatabaseConnector.insert(BINDING_TABLE, keyPairs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
