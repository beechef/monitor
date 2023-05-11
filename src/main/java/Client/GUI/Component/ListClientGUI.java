/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Client.GUI.Component;

import Client.GUI.Lib.ClientDTO;
import Client.GUI.Lib.GlobalVariable;
import java.awt.Container;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

/**
 *
 * @author Admin
 */
public class ListClientGUI extends javax.swing.JPanel {

    /**
     * Creates new form ListClientGUI
     */
    public ListClientGUI() {
        initComponents();
        //set placeHolder
        this.searchPanel1.setPlaceHolderText("Try to find ...");

        //set scrollbar
        setScrollbar(this.scroll);

        //render table
        renderTable(GlobalVariable.clientList);
        //search 
        handleSearch();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchPanel1 = new Client.GUI.Component.SearchPanel();
        headerTableClient1 = new Client.GUI.Component.HeaderTableClient();
        scroll = new javax.swing.JScrollPane();
        containerRowTable = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        scroll.setBorder(null);

        containerRowTable.setLayout(new javax.swing.BoxLayout(containerRowTable, javax.swing.BoxLayout.Y_AXIS));
        scroll.setViewportView(containerRowTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scroll)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(searchPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(headerTableClient1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(searchPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(headerTableClient1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scroll)
                .addGap(40, 40, 40))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel containerRowTable;
    private Client.GUI.Component.HeaderTableClient headerTableClient1;
    public javax.swing.JScrollPane scroll;
    private Client.GUI.Component.SearchPanel searchPanel1;
    // End of variables declaration//GEN-END:variables

    public void renderTable(List<ClientDTO> clientList) {
        //reset panel
        System.out.println("rerender");
        this.containerRowTable.removeAll();
        if (HeaderTableClient.stateSortArrListClient) {
            render(clientList);
        } else {
            //sort arr
            List<ClientDTO> stmpList = new ArrayList<>();
            clientList.forEach(e -> {
                if (e.getStatus()) {
                    stmpList.add(e);
                }
            });
            clientList.forEach(e -> {
                if (!e.getStatus()) {
                    stmpList.add(e);
                }
            });
            render(stmpList);
        }

    }

    private void render(List<ClientDTO> clientList) {
        clientList.forEach(e -> {
            this.containerRowTable.add(new RowTableClient(e));
        });
        if (GlobalVariable.main != null) {
            GlobalVariable.main.validate();
            GlobalVariable.main.repaint();
        }

    }

    public void handleUerLogout(String uuid) {

        for (int i = 0; i < GlobalVariable.clientList.size(); i++) {
            if (GlobalVariable.clientList.get(i).getID().equals(uuid)) {
                GlobalVariable.clientList.get(i).setStatus(false);
                GlobalVariable.clientList.get(i).setIpAdress(" ............ ");
                renderTable(GlobalVariable.clientList);
                break;
            }
        }
    }

    public void handleUerLogin(String name, String uuid, String host, boolean stmpStatus, int port,long writeLogInterval, boolean isWriteLog) {
        System.out.println(GlobalVariable.clientList.toString());
        boolean findUser = false;
        for (int i = 0; i < GlobalVariable.clientList.size(); i++) {
            if (GlobalVariable.clientList.get(i).getID().equals(uuid)) {
                GlobalVariable.clientList.get(i).setStatus(true);
                GlobalVariable.clientList.get(i).setIpAdress(host);
                findUser = true;
                break;
            }
        }

        if (findUser) {
            renderTable(GlobalVariable.clientList);
        } else {
            GlobalVariable.clientList.add(new ClientDTO(name, uuid, host, stmpStatus, port,writeLogInterval,isWriteLog));
            renderTable(GlobalVariable.clientList);
        }
    }

    public void changedName(String uuid, String name) {
        for (int i = 0; i < GlobalVariable.clientList.size(); i++) {
            if (GlobalVariable.clientList.get(i).getID().equals(uuid)) {
                GlobalVariable.clientList.get(i).setName(name);
                break;
            }
        }
        if(GlobalVariable.selectedClient){
            if(GlobalVariable.selectedClientInfor.getID().equals(uuid)){
                GlobalVariable.selectedClientInfor.setName(name);
                GlobalVariable.main.renderHeader(GlobalVariable.main);
            }
        }
        
        renderTable(GlobalVariable.clientList);
    }

    private void handleSearch() {
        this.searchPanel1.InputSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (searchPanel1.InputSearch.getText().isEmpty()) {
                        GlobalVariable.clientListStmp.removeAll(GlobalVariable.clientListStmp);

                        renderTable(GlobalVariable.clientList);
                    } else {
                        if (!searchPanel1.InputSearch.getText().equals(searchPanel1.getPlaceHolderText())) {
                            GlobalVariable.clientListStmp.removeAll(GlobalVariable.clientListStmp);

                            GlobalVariable.clientList.forEach(e -> {
                                if (e.getName().contains(searchPanel1.InputSearch.getText())) {
                                    GlobalVariable.clientListStmp.add(e);
                                }
                            });

                            renderTable(GlobalVariable.clientListStmp);
                        }
                    }
                }

            }
        });
    }

    private void setScrollbar(JScrollPane s) {
        s.setComponentZOrder(s.getVerticalScrollBar(), 0);
        s.setComponentZOrder(s.getViewport(), 1);
        s.getVerticalScrollBar().setOpaque(false);
        s.setLayout(new ScrollPaneLayout() {
            @Override
            public void layoutContainer(Container parent) {
                JScrollPane scrollPane = (JScrollPane) parent;

                Rectangle availR = scrollPane.getBounds();
                availR.x = availR.y = 0;

                Insets parentInsets = parent.getInsets();
                availR.x = parentInsets.left;
                availR.y = parentInsets.top;
                availR.width -= parentInsets.left + parentInsets.right;
                availR.height -= parentInsets.top + parentInsets.bottom;

                Rectangle vsbR = new Rectangle();
                vsbR.width = 5;
                vsbR.height = availR.height;
                vsbR.x = availR.x + availR.width - vsbR.width;
                vsbR.y = availR.y;

                if (viewport != null) {
                    viewport.setBounds(availR);
                }
                if (vsb != null) {
                    vsb.setVisible(true);
                    vsb.setBounds(vsbR);
                }
            }
        });
        s.getVerticalScrollBar().setUI(new MyScrollBarUI());
    }
}
