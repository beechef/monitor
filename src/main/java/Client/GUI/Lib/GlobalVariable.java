/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client.GUI.Lib;

import Client.GUI.Admin.MainGUI;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class GlobalVariable {
    //current path
    public static String currentPath ;
    
    //color
    public static final Color primaryColor=new java.awt.Color(46, 79, 79);
    public static final Color activeColor=new java.awt.Color(76, 126, 126);
    public static final Color headerColor=new java.awt.Color(236, 242,255);
    
    //selected client
    public static boolean selectedClient=true;
    
    //init sidebar item list
    public static List<SidebarItemDTO> itemList=new ArrayList<>();
    
    //init jpanel content
    public static JPanel listClient;
    public static JPanel hardware;
    public static JPanel process;
    public static JPanel streaming;
    public static JPanel others;
    public static JPanel profile;

    
    //sidebar icon url
    public static final String urlMenuIcon1="/src/main/java/Client/GUI/Image/icon1.png";
    public static final String urlMenuIcon2="/src/main/java/Client/GUI/Image/Chart.png";
    public static final String urlMenuIcon3="/src/main/java/Client/GUI/Image/table.png";
    public static final String urlMenuIcon4="/src/main/java/Client/GUI/Image/desktop.png";
    public static final String urlMenuIcon5="/src/main/java/Client/GUI/Image/darhboard.png";
    public static final String urlMenuIcon6="/src/main/java/Client/GUI/Image/userBox.png";
    public static final String urlMenuIcon7="/src/main/java/Client/GUI/Image/Out.png";
    
    
    //Main farme
    public static MainGUI main=null;


    public static String getCurrentPath() {
        return currentPath;
    }
    
    
    
}
