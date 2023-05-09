package Server.MiddleWare;

public class AdminRequest {
    public int adminId;
    public String adminUuid;

    public AdminRequest(int adminId, String adminUuid) {
        this.adminId = adminId;
        this.adminUuid = adminUuid;
    }
}
