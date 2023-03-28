/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client.GUI.Lib;

import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class SidebarItemDTO {
    public  JPanel compTitle=null;
    public  JPanel compContent=null;
    public  boolean state=false;
    public  boolean clientRequest=false;

    public SidebarItemDTO() {
    }
    
    public SidebarItemDTO(JPanel compTitle, JPanel compContent, boolean state, boolean clientRequest) {
        this.compTitle=compTitle;
        this.compContent=compContent;
        this.state=state;
        this.clientRequest=clientRequest;
    }
    
    @Override
    public String toString() {
        return " state=" + state ;
    }
    
}
