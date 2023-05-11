/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Client.GUI.Admin;

import Client.ClientInstance;
import Client.GUI.Component.ListClientGUI;
import Client.GUI.Component.HardwareGUI;
import Client.GUI.Component.OtherGUI;
import Client.GUI.Component.ProfileGUI;
import Client.GUI.Component.StreamingGUI;
import Client.GUI.Component.ProcessGUI;
import Client.GUI.Lib.GlobalVariable;
import Client.GUI.Lib.RoundBorder;
import Client.GUI.Lib.SidebarItemDTO;
import SocketMessageReceiver.DataType.GetHardwareInfo.GetHardwareInfoAdminSide;
import SocketMessageReceiver.DataType.GetProcesses.GetProcessesAdminSide;
import SocketMessageSender.CustomAdminSender.GetHardwareInfoSender;
import SocketMessageSender.CustomAdminSender.GetProcessesSender;

import java.awt.Cursor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 * @author Admin
 */
public class MainGUI extends javax.swing.JFrame {

    /**
     * Creates new form MainGUI
     */
    public MainGUI() throws IOException {
        initComponents();
        //init current path
        GlobalVariable.currentPath = new java.io.File(".").getCanonicalPath();

        //init content
        GlobalVariable.listClient = new ListClientGUI();
        GlobalVariable.hardware = new HardwareGUI();
        GlobalVariable.process = new ProcessGUI();
        GlobalVariable.streaming = new StreamingGUI();
        GlobalVariable.others = new OtherGUI();
        GlobalVariable.profile = new ProfileGUI();

//        this.content.add(icon1)
        //init sidebar item
        GlobalVariable.itemList.add(0, new SidebarItemDTO(this.MenuItem, GlobalVariable.listClient, true, false));
        GlobalVariable.itemList.add(1, new SidebarItemDTO(this.MenuItem1, GlobalVariable.hardware, false, true));
        GlobalVariable.itemList.add(2, new SidebarItemDTO(this.MenuItem2, GlobalVariable.process, false, true));
        GlobalVariable.itemList.add(3, new SidebarItemDTO(this.MenuItem3, GlobalVariable.streaming, false, true));
        GlobalVariable.itemList.add(4, new SidebarItemDTO(this.MenuItem4, GlobalVariable.others, false, true));
        GlobalVariable.itemList.add(5, new SidebarItemDTO(this.MenuItem5, null, false, false));
        GlobalVariable.itemList.add(6, new SidebarItemDTO(this.MenuItem6, null, false, false));

        //add content panel
        addContentPanel(this);

        //add icon
        setItemIcons();

        //active menu item
        handelActiveMenuItem();

        //add hover item list
        sidebarItemMouseEvent();

        //header
        //renderHeader
        renderHeader(this);

    }

    public void renderSidebar() throws IOException {
        //active menu item
        handelActiveMenuItem();
        //add hover item list
        sidebarItemMouseEvent();
        //renderHeader
        renderHeader(this);
    }

    public void setIdAdmin() {
        this.idAdmin.setText("   ID: " + GlobalVariable.idAdmin);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        container = new javax.swing.JPanel();
        Sidebar = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        MenuItem = new javax.swing.JPanel();
        icon1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        MenuItem1 = new javax.swing.JPanel();
        icon2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        MenuItem2 = new javax.swing.JPanel();
        icon3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        MenuItem3 = new javax.swing.JPanel();
        icon4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        MenuItem4 = new javax.swing.JPanel();
        icon5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        MenuItem5 = new javax.swing.JPanel();
        icon6 = new javax.swing.JLabel();
        idAdmin = new javax.swing.JLabel();
        MenuItem6 = new javax.swing.JPanel();
        icon7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        Header = new javax.swing.JPanel();
        ClientName = new javax.swing.JLabel();
        ClientID = new javax.swing.JLabel();
        ClientAddress = new javax.swing.JLabel();
        content = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        container.setBackground(new java.awt.Color(255, 255, 255));
        container.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(46, 79, 79)));
        container.setLayout(new java.awt.BorderLayout());

        Sidebar.setBackground(new java.awt.Color(46, 79, 79));
        Sidebar.setMinimumSize(new java.awt.Dimension(220, 100));
        Sidebar.setPreferredSize(new java.awt.Dimension(250, 621));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Monitor ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Menu");

        MenuItem.setOpaque(false);
        MenuItem.setLayout(new java.awt.BorderLayout());

        icon1.setBackground(new java.awt.Color(255, 255, 255));
        icon1.setForeground(new java.awt.Color(255, 255, 255));
        icon1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon1.setMaximumSize(new java.awt.Dimension(42, 16));
        icon1.setMinimumSize(new java.awt.Dimension(42, 16));
        icon1.setPreferredSize(new java.awt.Dimension(42, 25));
        MenuItem.add(icon1, java.awt.BorderLayout.LINE_START);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("   List clients");
        MenuItem.add(jLabel7, java.awt.BorderLayout.CENTER);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Feature");

        MenuItem1.setOpaque(false);
        MenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuItem1MouseClicked(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                MenuItem1MouseExited(evt);
            }
        });
        MenuItem1.setLayout(new java.awt.BorderLayout());

        icon2.setBackground(new java.awt.Color(255, 255, 255));
        icon2.setForeground(new java.awt.Color(255, 255, 255));
        icon2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon2.setMaximumSize(new java.awt.Dimension(42, 16));
        icon2.setMinimumSize(new java.awt.Dimension(42, 16));
        icon2.setPreferredSize(new java.awt.Dimension(42, 25));
        MenuItem1.add(icon2, java.awt.BorderLayout.LINE_START);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("   Hardware");
        MenuItem1.add(jLabel8, java.awt.BorderLayout.CENTER);

        MenuItem2.setOpaque(false);
        MenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuItem2MouseClicked(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                MenuItem2MouseExited(evt);
            }
        });
        MenuItem2.setLayout(new java.awt.BorderLayout());

        icon3.setBackground(new java.awt.Color(255, 255, 255));
        icon3.setForeground(new java.awt.Color(255, 255, 255));
        icon3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon3.setMaximumSize(new java.awt.Dimension(42, 16));
        icon3.setMinimumSize(new java.awt.Dimension(42, 16));
        icon3.setPreferredSize(new java.awt.Dimension(42, 25));
        MenuItem2.add(icon3, java.awt.BorderLayout.LINE_START);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("   Process");
        MenuItem2.add(jLabel9, java.awt.BorderLayout.CENTER);

        MenuItem3.setOpaque(false);
        MenuItem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OpenStreamingTab(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                MenuItem3MouseExited(evt);
            }
        });
        MenuItem3.setLayout(new java.awt.BorderLayout());

        icon4.setBackground(new java.awt.Color(255, 255, 255));
        icon4.setForeground(new java.awt.Color(255, 255, 255));
        icon4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon4.setMaximumSize(new java.awt.Dimension(42, 16));
        icon4.setMinimumSize(new java.awt.Dimension(42, 16));
        icon4.setPreferredSize(new java.awt.Dimension(42, 25));
        MenuItem3.add(icon4, java.awt.BorderLayout.LINE_START);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("   Streaming");
        MenuItem3.add(jLabel10, java.awt.BorderLayout.CENTER);

        MenuItem4.setOpaque(false);
        MenuItem4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                MenuItem4MouseExited(evt);
            }
        });
        MenuItem4.setLayout(new java.awt.BorderLayout());

        icon5.setBackground(new java.awt.Color(255, 255, 255));
        icon5.setForeground(new java.awt.Color(255, 255, 255));
        icon5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon5.setMaximumSize(new java.awt.Dimension(42, 16));
        icon5.setMinimumSize(new java.awt.Dimension(42, 16));
        icon5.setPreferredSize(new java.awt.Dimension(42, 25));
        MenuItem4.add(icon5, java.awt.BorderLayout.LINE_START);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("   Others");
        MenuItem4.add(jLabel11, java.awt.BorderLayout.CENTER);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 204, 204));
        jLabel12.setText("Profile");

        MenuItem5.setOpaque(false);
        MenuItem5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                MenuItem5MouseExited(evt);
            }
        });
        MenuItem5.setLayout(new java.awt.BorderLayout());

        icon6.setBackground(new java.awt.Color(255, 255, 255));
        icon6.setForeground(new java.awt.Color(255, 255, 255));
        icon6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon6.setMaximumSize(new java.awt.Dimension(42, 16));
        icon6.setMinimumSize(new java.awt.Dimension(42, 16));
        icon6.setPreferredSize(new java.awt.Dimension(42, 25));
        MenuItem5.add(icon6, java.awt.BorderLayout.LINE_START);

        idAdmin.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        idAdmin.setForeground(new java.awt.Color(255, 255, 255));
        idAdmin.setText("   ID: 123123");
        MenuItem5.add(idAdmin, java.awt.BorderLayout.CENTER);

        MenuItem6.setOpaque(false);
        MenuItem6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                MenuItem6MouseExited(evt);
            }
        });
        MenuItem6.setLayout(new java.awt.BorderLayout());

        icon7.setBackground(new java.awt.Color(255, 255, 255));
        icon7.setForeground(new java.awt.Color(255, 255, 255));
        icon7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon7.setMaximumSize(new java.awt.Dimension(42, 16));
        icon7.setMinimumSize(new java.awt.Dimension(42, 16));
        icon7.setPreferredSize(new java.awt.Dimension(42, 25));
        MenuItem6.add(icon7, java.awt.BorderLayout.LINE_START);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("   Logout");
        MenuItem6.add(jLabel14, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout SidebarLayout = new javax.swing.GroupLayout(Sidebar);
        Sidebar.setLayout(SidebarLayout);
        SidebarLayout.setHorizontalGroup(
                SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(SidebarLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SidebarLayout.createSequentialGroup()
                                                .addGroup(SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(MenuItem2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(MenuItem6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(MenuItem5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(MenuItem4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                                                        .addComponent(MenuItem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(MenuItem1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(MenuItem3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(15, 15, 15))))
        );
        SidebarLayout.setVerticalGroup(
                SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(SidebarLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MenuItem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MenuItem1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MenuItem2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MenuItem3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MenuItem4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MenuItem5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MenuItem6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(119, Short.MAX_VALUE))
        );

        container.add(Sidebar, java.awt.BorderLayout.LINE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        Header.setBackground(new java.awt.Color(255, 255, 255));
        Header.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Header.setMaximumSize(new java.awt.Dimension(32767, 50));
        Header.setMinimumSize(new java.awt.Dimension(900, 50));
        Header.setPreferredSize(new java.awt.Dimension(900, 50));

        ClientName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ClientName.setForeground(new java.awt.Color(51, 51, 51));
        ClientName.setText("List clients");

        ClientID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ClientID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        ClientAddress.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ClientAddress.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
        Header.setLayout(HeaderLayout);
        HeaderLayout.setHorizontalGroup(
                HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(HeaderLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(ClientName, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
                                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(ClientID, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                                        .addComponent(ClientAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(30, 30, 30))
        );
        HeaderLayout.setVerticalGroup(
                HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(HeaderLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(ClientID, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(ClientAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))
                        .addComponent(ClientName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.add(Header, java.awt.BorderLayout.PAGE_START);

        content.setBackground(new java.awt.Color(255, 255, 255));
        content.setMinimumSize(new java.awt.Dimension(900, 0));
        content.setPreferredSize(new java.awt.Dimension(900, 0));
        content.setRequestFocusEnabled(false);
        content.setLayout(new java.awt.CardLayout());
        jPanel2.add(content, java.awt.BorderLayout.CENTER);

        container.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(container, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void MenuItem1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuItem1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItem1MouseExited

    private void MenuItem2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuItem2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItem2MouseExited

    private void MenuItem3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuItem3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItem3MouseExited

    private void MenuItem4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuItem4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItem4MouseExited

    private void MenuItem5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuItem5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItem5MouseExited

    private void MenuItem6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuItem6MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItem6MouseExited

    private void MenuItem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuItem1MouseClicked
        // click to hardware
        if (GlobalVariable.selectedClient) {
            System.out.println("Amin request get hardware of user uuid: " + GlobalVariable.selectedClientInfor.getID());
            var sender = new GetHardwareInfoSender(ClientInstance.tcpClient);
            var hardwareTypes = new ArrayList<GetHardwareInfoAdminSide.HardwareType>();
            hardwareTypes.add(GetHardwareInfoAdminSide.HardwareType.CPU);
            hardwareTypes.add(GetHardwareInfoAdminSide.HardwareType.MEMORY);
            hardwareTypes.add(GetHardwareInfoAdminSide.HardwareType.DISK);

            sender.send(null, new GetHardwareInfoAdminSide(hardwareTypes, GlobalVariable.selectedClientInfor.getID(), GlobalVariable.tokenAdmin));
        }

    }//GEN-LAST:event_MenuItem1MouseClicked

    private void MenuItem2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuItem2MouseClicked
        // click to process
        if (GlobalVariable.selectedClient) {
            System.out.println("Amin request get prcess of user uuid: " + GlobalVariable.selectedClientInfor.getID());
            System.out.println(GlobalVariable.tokenAdmin);
            sendRequestGetAllProcess();
        }
    }//GEN-LAST:event_MenuItem2MouseClicked


    private void OpenStreamingTab(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuItem3MouseClicked
//        GlobalVariable.streaming.setVisible(true);
//        GlobalVariable.streaming.init();

    }//GEN-LAST:event_MenuItem3MouseClicked

    public void OpenStream() {
//        GlobalVariable.streaming.setVisible(true);
        GlobalVariable.streaming.init();
    }

    public void CloseStream() {
        GlobalVariable.streaming.reset();
    }

    public void sendRequestGetAllProcess() {
        var sender = new GetProcessesSender(ClientInstance.tcpClient);
        sender.send(null, new GetProcessesAdminSide(GlobalVariable.tokenAdmin, GlobalVariable.selectedClientInfor.getID()));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GlobalVariable.main = new MainGUI();
                    GlobalVariable.main.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel ClientAddress;
    public javax.swing.JLabel ClientID;
    private javax.swing.JLabel ClientName;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel MenuItem;
    private javax.swing.JPanel MenuItem1;
    public javax.swing.JPanel MenuItem2;
    private javax.swing.JPanel MenuItem3;
    private javax.swing.JPanel MenuItem4;
    private javax.swing.JPanel MenuItem5;
    private javax.swing.JPanel MenuItem6;
    public javax.swing.JPanel Sidebar;
    public static javax.swing.JPanel container;
    public javax.swing.JPanel content;
    private javax.swing.JLabel icon1;
    private javax.swing.JLabel icon2;
    private javax.swing.JLabel icon3;
    private javax.swing.JLabel icon4;
    private javax.swing.JLabel icon5;
    private javax.swing.JLabel icon6;
    private javax.swing.JLabel icon7;
    private javax.swing.JLabel idAdmin;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

    private void setItemIcons() throws IOException {
        this.icon1.setIcon(new ImageIcon(GlobalVariable.currentPath + GlobalVariable.urlMenuIcon1));
        this.icon2.setIcon(new ImageIcon(GlobalVariable.currentPath + GlobalVariable.urlMenuIcon2));
        this.icon3.setIcon(new ImageIcon(GlobalVariable.currentPath + GlobalVariable.urlMenuIcon3));
        this.icon4.setIcon(new ImageIcon(GlobalVariable.currentPath + GlobalVariable.urlMenuIcon4));
        this.icon5.setIcon(new ImageIcon(GlobalVariable.currentPath + GlobalVariable.urlMenuIcon5));
        this.icon6.setIcon(new ImageIcon(GlobalVariable.currentPath + GlobalVariable.urlMenuIcon6));
        this.icon7.setIcon(new ImageIcon(GlobalVariable.currentPath + GlobalVariable.urlMenuIcon7));

    }

    private void sidebarItemMouseEvent() {
        GlobalVariable.itemList.forEach(e -> {
            e.compTitle.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    //request pick client
                    if (e.clientRequest && GlobalVariable.selectedClient != true) {
                        return;
                    }

                    e.compTitle.setBorder(new RoundBorder(3, GlobalVariable.activeColor));
                    e.compTitle.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (e.state) {
                        return;
                    }
                    e.compTitle.setBorder(new RoundBorder(3, GlobalVariable.primaryColor));
                    e.compTitle.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    //set active
                    boolean checkExcepion = false;

                    for (int i = 0; i < GlobalVariable.itemList.size(); i++) {
                        if (e == GlobalVariable.itemList.get(i)) {
                            if (e.clientRequest && !GlobalVariable.selectedClient) {
                                checkExcepion = true;
                            }
                        }
                    }
                    if (!checkExcepion) {
                        e.compTitle.setBorder(new RoundBorder(3, GlobalVariable.activeColor));
                        e.compTitle.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                    for (int i = 0; i < GlobalVariable.itemList.size(); i++) {
                        if (e == GlobalVariable.itemList.get(i) && e.clientRequest && GlobalVariable.selectedClient || e == GlobalVariable.itemList.get(i) && !e.clientRequest) {
                            GlobalVariable.itemList.get(i).state = true;
                            //handel funcion
                            if (GlobalVariable.itemList.get(i).compContent != null) {
                                
                                //streaming 
                                if (GlobalVariable.itemList.get(i).compContent == GlobalVariable.streaming) {
                                    System.out.println("Streaming click");
                                    OpenStream();
                                } else {
                                    if (GlobalVariable.streaming.isStreaming) {
                                        System.out.println("close stream");
                                        CloseStream();
                                    }
                                }
                                
                                GlobalVariable.itemList.get(i).compContent.setVisible(true);
                                GlobalVariable.main.validate();
                                GlobalVariable.main.repaint();

                            } else {
                                System.out.println("logout");
                            }
                            //end handel function
                        } else {
                            if (checkExcepion) {
                                continue;
                            } else {
                                if (GlobalVariable.itemList.get(i).compContent != null) {
                                    GlobalVariable.itemList.get(i).compContent.setVisible(false);
                                }

                                GlobalVariable.itemList.get(i).state = false;
                                GlobalVariable.itemList.get(i).compTitle.setBorder(new RoundBorder(3, GlobalVariable.primaryColor));
                            }
                        }
                    }

                }

            });
        }
        );
    }

    private void handelActiveMenuItem() {
        GlobalVariable.itemList.forEach(e -> {
            if (e.state) {
                e.compTitle.setBorder(new RoundBorder(3, GlobalVariable.activeColor));
                e.compTitle.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            } else {
                e.compTitle.setBorder(new RoundBorder(3, GlobalVariable.primaryColor));
            }
        });

    }

    public void renderHeader(MainGUI f) {
        if (GlobalVariable.selectedClient) {
            f.ClientName.setText(GlobalVariable.selectedClientInfor.getName());
            f.ClientID.setText("Client uuid:" + GlobalVariable.selectedClientInfor.getID());
            f.ClientAddress.setText("IpAddress: " + GlobalVariable.selectedClientInfor.getIpAdress());
            f.ClientID.setVisible(true);
            f.ClientAddress.setVisible(true);

        } else {
            f.Header.setBackground(GlobalVariable.headerColor);
            f.ClientID.setVisible(false);
            f.ClientAddress.setVisible(false);
            f.ClientName.setText("List clients");
        }
    }

    private void addContentPanel(MainGUI f) {
        GlobalVariable.itemList.forEach(e -> {
            if (e.compContent != null) {
                f.content.add(e.compContent);
                e.compContent.setVisible(false);
            }
        });
    }
}
