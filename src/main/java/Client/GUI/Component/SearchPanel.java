/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Client.GUI.Component;

import Client.GUI.Lib.GlobalVariable;
import Client.GUI.Lib.RoundBorder;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Admin
 */
public class SearchPanel extends javax.swing.JPanel {

    private String placeHolderText;

    public String getPlaceHolderText() {
        return placeHolderText;
    }

    public void setPlaceHolderText(String placeHolderText) {
        this.placeHolderText = placeHolderText;
        handleSetPlaceHolder(this.InputSearch, placeHolderText);

    }

    public SearchPanel() {
        initComponents();
        //set Icon
        handleSetIcon(this.jLabel1);
        //set border
        this.setOpaque(false);
        this.setBorder(new RoundBorder(6, GlobalVariable.searchColor));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        InputSearch = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(213, 220, 220));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setPreferredSize(new java.awt.Dimension(2, 0));
        add(jLabel2);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setPreferredSize(new java.awt.Dimension(40, 0));
        add(jLabel1);

        InputSearch.setBackground(new java.awt.Color(213, 220, 220));
        InputSearch.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        InputSearch.setBorder(null);
        InputSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputSearchActionPerformed(evt);
            }
        });
        add(InputSearch);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setPreferredSize(new java.awt.Dimension(5, 0));
        add(jLabel3);
    }// </editor-fold>//GEN-END:initComponents

    private void InputSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField InputSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables

    private void handleSetPlaceHolder(JTextField inputSearch, String placeHolderText) {
        
        inputSearch.setText(placeHolderText);
        inputSearch.setForeground(Color.GRAY);

        inputSearch.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inputSearch.getText().equals(placeHolderText)) {
                    inputSearch.setText("");
                    inputSearch.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (inputSearch.getText().isEmpty()) {
                    inputSearch.setForeground(Color.GRAY);
                    inputSearch.setText(placeHolderText);
                }
            }
        });

    }

    private void handleSetIcon(JLabel j) {
        j.setIcon(new ImageIcon(GlobalVariable.currentPath + GlobalVariable.urlIconSearch));
    }
}