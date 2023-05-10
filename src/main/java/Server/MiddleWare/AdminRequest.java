package Server.MiddleWare;

import java.io.Serializable;

public class AdminRequest implements Serializable {
    public int adminId;
    public String adminUuid;

    public AdminRequest(int adminId, String adminUuid) {
        this.adminId = adminId;
        this.adminUuid = adminUuid;
    }
}
