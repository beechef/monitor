package SocketMessageReceiver.CustomServerReceiver;

import Server.Database.*;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.GetUserResultRequest;
import SocketMessageReceiver.DataType.LoginUserRequest;
import SocketMessageReceiver.DataType.LoginUserResult;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.LoginUserResultSender;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginUserReceiver extends SocketMessageReceiver<LoginUserRequest> {
    private static final String USER_TABLE = "user";
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";

    private static final String BINDING_TABLE = "binding";
    private static final String BINDING_ADMIN_ID_FIELD = "id_admin";
    private static final String BINDING_USER_ID_FIELD = "id_user";


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
            InetSocketAddress inetAddress = getInitAddress(user);
            var existUser = getUser(deviceId);
            var name = deviceId;

            if (existUser.next()) {
                name = existUser.getString(NAME_FIELD);
            } else {
                registerUser(deviceId, name);
                bindingAdminAndUser(adminId, deviceId);
            }

            var userInfo = new UserController.UserInfo(name, deviceId, UserController.UserInfo.UserStatus.AVAILABLE, inetAddress, user);
            UserController.addTcpUser(adminId, userInfo);

            var sender = new LoginUserResultSender(server);
            var admins = UserController.getTcpAdmins(adminId);

            for (var admin : admins) {
                sender.send(admin.socket, new LoginUserResult(new GetUserResultRequest.UserInfo(userInfo)));
            }

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

    private ResultSet getUser(String id) {
        var conditions = new Condition[]{new Condition(ID_FIELD, Operator.Equal, id, CombineCondition.NONE)};
        try {
            return DatabaseConnector.select(USER_TABLE, null, conditions);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void registerUser(String id, String name) {
        try {
            var values = new String[]{ID_FIELD, NAME_FIELD};
            var data = new String[]{id, name};
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
            var values = new String[]{BINDING_ADMIN_ID_FIELD, BINDING_USER_ID_FIELD};
            var data = new String[]{String.valueOf(adminId), userId};
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
