package SocketMessageReceiver.DataType;

import Server.UserController;

import java.io.Serializable;
import java.util.ArrayList;

public class GetUserResultRequest implements Serializable {
    public static class UserInfo implements Serializable {
        public String uuid;
        public String host;
        public int port;

        public UserInfo(String uuid, String host, int port) {
            this.uuid = uuid;
            this.host = host;
            this.port = port;
        }
    }

    public ArrayList<UserInfo> userInfos;

    public GetUserResultRequest(ArrayList<UserController.UserInfo> userInfos) {
        this.userInfos = new ArrayList<>();

        for (var userInfo : userInfos) {
            var uuid = userInfo.uuid;
            var host = userInfo.inetSocketAddress.getAddress().toString();
            var port = userInfo.inetSocketAddress.getPort();

            this.userInfos.add(new UserInfo(uuid, host, port));
        }
    }
}
