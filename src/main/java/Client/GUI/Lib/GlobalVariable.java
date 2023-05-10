package Client.GUI.Lib;

import Client.GUI.Admin.LoginGUI;
import Client.GUI.Admin.MainGUI;
import Client.GUI.Admin.RegisterGUI;
import Client.GUI.Component.HardwareGUI;
import Client.GUI.Component.ListClientGUI;
import Client.GUI.Component.OthersGUI;
import Client.GUI.Component.ProcessGUI;
import Client.GUI.Component.ProfileGUI;
import Client.GUI.Component.StreamingGUI;
import Client.GUI.User.LoginUserGUI;
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
    public static String currentPath;

    //color
    public static final Color primaryColor = new java.awt.Color(46, 79, 79);
    public static final Color activeColor = new java.awt.Color(76, 126, 126);
    public static final Color headerColor = new java.awt.Color(236, 242, 255);
    public static final Color searchColor = new java.awt.Color(213, 220, 220);
    public static final Color HeaderTableColor = new java.awt.Color(203, 228, 222);
    public static final Color WarningColor = new java.awt.Color(220, 53, 53);

    //selected client
    public static boolean selectedClient = false;
    public static ClientDTO selectedClientInfor=null;

    public static LoginGUI LoginAdminGUI;
    public static MainGUI main = null;
    public static RegisterGUI RegisterAdminGUI;
    public static LoginUserGUI LoginUserGUI;

    //init sidebar item list
    public static List<SidebarItemDTO> itemList = new ArrayList<>();

    //init jpanel content
    public static ListClientGUI listClient;
    public static HardwareGUI hardware;
    public static ProcessGUI process;
    public static StreamingGUI streaming;
    public static OthersGUI others;
    public static ProfileGUI profile;

    //sidebar icon url
    public static final String urlMenuIcon1 = "/src/main/java/Client/GUI/Image/icon1.png";
    public static final String urlMenuIcon2 = "/src/main/java/Client/GUI/Image/Chart.png";
    public static final String urlMenuIcon3 = "/src/main/java/Client/GUI/Image/table.png";
    public static final String urlMenuIcon4 = "/src/main/java/Client/GUI/Image/desktop.png";
    public static final String urlMenuIcon5 = "/src/main/java/Client/GUI/Image/darhboard.png";
    public static final String urlMenuIcon6 = "/src/main/java/Client/GUI/Image/userBox.png";
    public static final String urlMenuIcon7 = "/src/main/java/Client/GUI/Image/Out.png";
    public static final String urlIconSearch = "/src/main/java/Client/GUI/Image/Search.png";
    public static final String urlCloseIcon = "/src/main/java/Client/GUI/Image/close.png";
    public static final String urlIconArrow = "/src/main/java/Client/GUI/Image/arrow.png";
    public static final String urlIconAction = "/src/main/java/Client/GUI/Image/icon_action.png";

    //Main farme
    public static String getCurrentPath() {
        return currentPath;
    }
    
    public static String tokenAdmin=null;

    //list client
    public static List<ClientDTO> clientList = new ArrayList<>();
    // stmp arr client 
    public static List<ClientDTO> clientListStmp = new ArrayList<>();

    //list process
    public static List<ProcessDTO> processList = new ArrayList<>();
    //stmp arr process
    public static List<ProcessDTO> processListStmp = new ArrayList<>();

}
