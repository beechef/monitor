package SocketMessageReceiver.DataType;

import Server.UserController;

import java.io.Serializable;
import java.util.ArrayList;

public class GetUserResultRequest implements Serializable {
    public static class UserInfo implements Serializable {
        public String uuid;
        public String name;
        public String host;
        public boolean isWriteLog;
        public long writeLogInterval;
        public UserController.UserInfo.UserStatus status;
        public int port;

        public UserInfo(UserController.UserInfo info) {
            this.uuid = info.uuid;
            this.name = info.name;
            this.isWriteLog = info.isWriteLog;
            this.writeLogInterval = info.writeLogInterval;
            this.status = info.status;
            this.host = info.inetSocketAddress == null ? "" : info.inetSocketAddress.getAddress().toString();
            this.port = info.inetSocketAddress == null ? 0 : info.inetSocketAddress.getPort();
        }
    }

    public ArrayList<UserInfo> userInfos;

    public GetUserResultRequest(ArrayList<UserController.UserInfo> userInfos) {
        this.userInfos = new ArrayList<>();

        for (var userInfo : userInfos) {
            this.userInfos.add(new UserInfo(userInfo));
        }
    }
}
