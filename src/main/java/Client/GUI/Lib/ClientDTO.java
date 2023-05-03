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

    public ClientDTO() {
    }

    public ClientDTO(String name, String uuid, String IpAdress, boolean status, int port) {
        this.name = name;
        this.uuid = uuid;
        this.IpAdress = IpAdress;
        this.status = status;
        this.port=port;
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
