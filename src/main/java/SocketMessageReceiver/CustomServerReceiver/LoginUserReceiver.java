package SocketMessageReceiver.CustomServerReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.LoginUserRequest;
import SocketMessageReceiver.SocketMessageReceiver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;

public class LoginUserReceiver extends SocketMessageReceiver<LoginUserRequest> {
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
            InetSocketAddress inetAddress;
            inetAddress = getInitAddress(user);

            var userInfo = new UserController.UserInfo(deviceId, inetAddress, user);
            UserController.addTcpUser(adminId, userInfo);

        } catch (IOException e) {
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
}
