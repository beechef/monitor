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
    private int ID;
    private String IpAdress;
    private boolean status;

    public ClientDTO() {
    }

    public ClientDTO(String name, int ID, String IpAdress, boolean status) {
        this.name = name;
        this.ID = ID;
        this.IpAdress = IpAdress;
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public int getID() {
        return ID;
    }

    public String getIpAdress() {
        return IpAdress;
    }

    public boolean getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ClientDTO{" + "name=" + name + ", ID=" + ID + ", IpAdress=" + IpAdress + ", status=" + status + '}';
    }
    
    
}
