package Server;

import Server.EventDispatcher.EventDispatcher;
import Server.EventDispatcher.EventName;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class UserController {
    public static class UserInfo implements Serializable {
        public String uuid;
        public String name;
        public UserStatus status;
        public InetSocketAddress inetSocketAddress;
        public Object tcpSocket;
        public Object udpSocket;

        public UserInfo(String uuid, UserStatus status, InetSocketAddress inetSocketAddress, Object tcpSocket) {
            this.name = uuid;
            this.uuid = uuid;
            this.status = status;
            this.inetSocketAddress = inetSocketAddress;
            this.tcpSocket = tcpSocket;
        }

        public UserInfo(String uuid, String name, UserStatus status, InetSocketAddress inetSocketAddress, Object tcpSocket) {
            this.uuid = uuid;
            this.name = name;
            this.status = status;
            this.inetSocketAddress = inetSocketAddress;
            this.tcpSocket = tcpSocket;
        }

        public enum UserStatus {
            AVAILABLE,
            UNAVAILABLE
        }
    }

    public static class AdminInfo implements Serializable {
        public int id;
        public int adminId;
        public Object tcpSocket;
        public Object udpSocket;

        public AdminInfo(int adminId, Object tcpSocket) {
            this.adminId = adminId;
            this.tcpSocket = tcpSocket;

            this.id = adminId;
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

    public static void removeUser(int adminId, UserInfo user) {
        if (users.containsKey(adminId)) {
            users.get(adminId).remove(user);
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


    public static AdminInfo getAdmin(int adminId) {
        if (admins.containsKey(adminId)) {
            for (var admin : admins.get(adminId)) {
                if (admin.adminId == adminId) {
                    return admin;
                }
            }
        }

        return null;
    }

    public static AdminInfo getAdmin(int adminId, Object socket) {
        if (admins.containsKey(adminId)) {
            for (var admin : admins.get(adminId)) {
                if (admin.tcpSocket == socket) {
                    return admin;
                }
            }
        }

        return null;
    }

    public static ArrayList<AdminInfo> getAdmins(int id) {
        var adminInfos = new ArrayList<AdminInfo>();

        if (admins.containsKey(id)) {
            adminInfos.addAll(admins.get(id));
        }

        return adminInfos;
    }
}
