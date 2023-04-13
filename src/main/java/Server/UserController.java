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
        public InetSocketAddress inetSocketAddress;
        public Object socket;

        public UserInfo(String uuid, InetSocketAddress inetSocketAddress, Object socket) {
            this.uuid = uuid;
            this.inetSocketAddress = inetSocketAddress;
            this.socket = socket;
        }
    }

    public static class AdminInfo implements Serializable {
        public int id;
        public int adminId;
        public Object socket;

        public AdminInfo(int adminId, Object socket) {
            this.adminId = adminId;
            this.socket = socket;

            id = socket.hashCode();
        }
    }

    private static final HashMap<Integer, ArrayList<UserInfo>> tcpUsers = new HashMap<>();
    private static final HashMap<Integer, ArrayList<AdminInfo>> tcpAdmins = new HashMap<>();

    public static void registerEvents() {
        EventDispatcher.startListening(EventName.USER_DISCONNECTED, UserController::removeDisconnectedSocket);
    }

    public static void unRegisterEvents() {
        EventDispatcher.stopListening(EventName.USER_DISCONNECTED, UserController::removeDisconnectedSocket);
    }

    private static void removeDisconnectedSocket(Object data) {
        for (var admins : tcpAdmins.values()) {
            if (admins.stream().anyMatch(x -> x.socket == data)) {
                admins.removeIf(x -> x.socket == data);
                return;
            }
        }

        for (var users : tcpUsers.values()) {
            if (users.stream().anyMatch(x -> x.socket == data)) {
                users.removeIf(x -> x.socket == data);
                return;
            }
        }
    }


    public static void addTcpUser(int adminId, UserInfo user) {
        if (tcpUsers.containsKey(adminId)) {
            tcpUsers.get(adminId).add(user);
        } else {
            var users = new ArrayList<UserInfo>();
            users.add(user);
            tcpUsers.put(adminId, users);
        }
    }

    public static void removeTcpUser(int adminId, UserInfo user) {
        if (tcpUsers.containsKey(adminId)) {
            tcpUsers.get(adminId).remove(user);
        }
    }

    public static void addTcpAdmin(AdminInfo admin) {
        var adminId = admin.adminId;
        if (tcpAdmins.containsKey(adminId)) {
            tcpAdmins.get(adminId).add(admin);
        } else {
            var admins = new ArrayList<AdminInfo>();
            admins.add(admin);
            tcpAdmins.put(adminId, admins);
        }
    }

    public static void removeTcpAdmin(AdminInfo admin) {
        var adminId = admin.adminId;
        if (tcpAdmins.containsKey(adminId)) {
            tcpAdmins.get(adminId).remove(admin);
        }
    }

    public static ArrayList<UserInfo> getTcpUsers(int adminId) {
        return tcpUsers.get(adminId);
    }

    public static UserInfo getTcpUser(int adminId, String uuid) {
        if (tcpUsers.containsKey(adminId)) {
            for (var user : tcpUsers.get(adminId)) {
                if (user.uuid.equals(uuid)) {
                    return user;
                }
            }
        }

        return null;
    }

    public static AdminInfo getTcpAdmin(int adminId, int id) {
        if (tcpAdmins.containsKey(adminId)) {
            for (var admin : tcpAdmins.get(adminId)) {
                if (admin.id == id) {
                    return admin;
                }
            }
        }

        return null;
    }

    public static AdminInfo getTcpAdmin(int adminId, Object socket) {
        if (tcpAdmins.containsKey(adminId)) {
            for (var admin : tcpAdmins.get(adminId)) {
                if (admin.socket == socket) {
                    return admin;
                }
            }
        }

        return null;
    }

    public static AdminInfo getTcpAdmin(int id) {
        for (var admins : tcpAdmins.values()) {
            for (var admin : admins) {
                if (admin.id == id) {
                    return admin;
                }
            }
        }

        return null;
    }
}
