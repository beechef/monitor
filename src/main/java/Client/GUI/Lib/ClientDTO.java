/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client.GUI.Lib;

/**
 *
 * @author Admin
 */
public class ClientDTO {

    private String name;
    private String uuid;
    private String IpAdress;
    private boolean status;
    private int port;
    private boolean isWriteLog = false;
    private long writeLogInterval;

    public ClientDTO() {
    }

    public ClientDTO(String name, String uuid, String IpAdress, boolean status, int port, long writeLogInterval, boolean isWriteLog) {
        this.name = name;
        this.uuid = uuid;
        if (this.IpAdress== null) {
            this.IpAdress = "             ";
        } else {
            this.IpAdress = IpAdress;

        }
        this.status = status;
        this.port = port;
        this.writeLogInterval = writeLogInterval;
        this.isWriteLog = isWriteLog;
    }

    public String getUuid() {
        return uuid;
    }

    public long getWriteLogInterval() {
        return writeLogInterval;
    }

    public void setIsWriteLog(boolean isWriteLog) {
        this.isWriteLog = isWriteLog;
    }

    public boolean getIsWriteLog() {
        return isWriteLog;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setWriteLogInterval(long writeLogInterval) {
        this.writeLogInterval = writeLogInterval;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setID(String uuid) {
        this.uuid = uuid;
    }

    public void setIpAdress(String IpAdress) {
        this.IpAdress = IpAdress;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return uuid;
    }

    public String getIpAdress() {
        return IpAdress;
    }

    public boolean getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ClientDTO{" + "name=" + name + ", uuid=" + uuid + ", IpAdress=" + IpAdress + ", status=" + status + ", port=" + port + '}';
    }

}
