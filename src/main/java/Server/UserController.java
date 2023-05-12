package Server;

import Server.EventDispatcher.EventDispatcher;
import Server.EventDispatcher.EventName;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserController {
    public static class UserInfo implements Serializable {
        public String uuid;
        public String name;
        public UserStatus status;
        public boolean isWriteLog;
        public long writeLogInterval;

        public InetSocketAddress inetSocketAddress;
        public Object tcpSocket;
        public Object udpSocket;

        public UserInfo(String uuid, String name, UserStatus status, boolean isWriteLog, long writeLogInterval, InetSocketAddress inetSocketAddress, Object tcpSocket) {
            this.uuid = uuid;
            this.name = name;
            this.status = status;
            this.isWriteLog = isWriteLog;
            this.writeLogInterval = writeLogInterval;
            this.inetSocketAddress = inetSocketAddress;
            this.tcpSocket = tcpSocket;
        }

        public enum UserStatus {
            AVAILABLE,
            UNAVAILABLE
        }
    }

    public static class AdminInfo implements Serializable {
        public String uuid;
        public int adminId;
        public Object tcpSocket;
        public Object udpSocket;

        public AdminInfo(String uuid, int adminId, Object tcpSocket) {
            this.uuid = uuid;
            this.adminId = adminId;
            this.tcpSocket = tcpSocket;
        }
    }

    private static final HashMap<Integer, ArrayList<UserInfo>> users = new HashMap<>();
    private static final HashMap<Integer, ArrayList<AdminInfo>> admins = new HashMap<>();

    public static void registerEvents() {
        EventDispatcher.startListening(EventName.USER_DISCONNECTED, UserController::removeDisconnectedSocket);
    }

    public static void unRegisterEvents() {
        EventDispatcher.stopListening(EventName.USER_DISCONNECTED, UserController::removeDisconnectedSocket);
    }

    private static void removeDisconnectedSocket(Object data) {
        for (var admins : admins.values()) {
            if (admins.stream().anyMatch(x -> x.tcpSocket == data || x.udpSocket == data)) {
                admins.removeIf(x -> x.tcpSocket == data);
                return;
            }
        }

        for (var users : users.values()) {
            if (users.stream().anyMatch(x -> x.tcpSocket == data || x.udpSocket == data)) {
                users.removeIf(x -> x.tcpSocket == data);
                return;
            }
        }
    }


    public static void addUser(int adminId, UserInfo user) {
        if (users.containsKey(adminId)) {
            users.get(adminId).add(user);
        } else {
            var users = new ArrayList<UserInfo>();
            users.add(user);
            UserController.users.put(adminId, users);
        }
    }

    public static void removeUser(int adminId, String uuid) {
        if (users.containsKey(adminId)) {
            users.get(adminId).removeIf(x -> x.uuid.equals(uuid));
        }
    }

    public static void addAdmin(AdminInfo admin) {
        var adminId = admin.adminId;
        if (admins.containsKey(adminId)) {
            admins.get(adminId).add(admin);
        } else {
            var admins = new ArrayList<AdminInfo>();
            admins.add(admin);
            UserController.admins.put(adminId, admins);
        }
    }

    public static void removeAdmin(AdminInfo admin) {
        var adminId = admin.adminId;
        if (admins.containsKey(adminId)) {
            admins.get(adminId).remove(admin);
        }
    }

    public static void removeAdmin(int adminId, String uuid){
        if (admins.containsKey(adminId)) {
            admins.get(adminId).removeIf(x -> x.uuid.equals(uuid));
        }
    }

    public static ArrayList<UserInfo> getUsers(int adminId) {
        return users.get(adminId);
    }

    public static UserInfo getUser(int adminId, String uuid) {
        if (users.containsKey(adminId)) {
            for (var user : users.get(adminId)) {
                if (user.uuid.equals(uuid)) {
                    return user;
                }
            }
        }

        return null;
    }


    public static AdminInfo getAdmin(int adminId, String uuid) {
        if (admins.containsKey(adminId)) {
            for (var admin : admins.get(adminId)) {
                if (admin.adminId == adminId && admin.uuid.equals(uuid)) {
                    return admin;
                }
            }
        }

        return null;
    }


//    public static AdminInfo getAdmin(int adminId, Object socket) {
//        if (admins.containsKey(adminId)) {
//            for (var admin : admins.get(adminId)) {
//                if (admin.tcpSocket == socket || admin.udpSocket == socket) {
//                    return admin;
//                }
//            }
//        }
//
//        return null;
//    }

    public static ArrayList<AdminInfo> getAdmins(int id) {
        var adminInfos = new ArrayList<AdminInfo>();

        if (admins.containsKey(id)) {
            adminInfos.addAll(admins.get(id));
        }

        return adminInfos;
    }
}
