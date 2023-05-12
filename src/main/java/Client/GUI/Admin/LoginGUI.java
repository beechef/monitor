package Client.GUI.Admin;

import Client.ClientInstance;
import Client.GUI.Lib.GlobalFunction;
import Client.GUI.Lib.GlobalVariable;
import Client.GUI.Lib.RoundBorder;
import Key.JWTKey;
import Server.EventDispatcher.EventDispatcher;
import SocketMessageReceiver.CustomAdminReceiver.LoginResultReceiver;
import SocketMessageReceiver.CustomServerReceiver.LoginReceiver;
import SocketMessageReceiver.DataType.*;
import SocketMessageReceiver.DataType.GetProcesses.GetProcessesAdminSide;
import SocketMessageReceiver.DataType.ProcessAction.ProcessActionRequestAdminSide;
import SocketMessageSender.CustomAdminSender.*;
import Utilities.Utilities;
import io.jsonwebtoken.Jwts;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Admin
 */
public class LoginGUI extends javax.swing.JFrame {

    /**
     * Creates new form LoginGUI
     */
    Color primaryColor = new java.awt.Color(46, 79, 79);

    private final LoginSender sender;

    public LoginGUI() {
        initComponents();
        this.img.setBorder(new RoundBorder(10, Color.ORANGE));
        this.wrapperRightPanel.setBorder(new RoundBorder(10, primaryColor));
        this.lableMessage.setForeground(GlobalVariable.WarningColor);
        sender = new LoginSender(ClientInstance.tcpClient);
        registerEvent();
    }

    private void registerEvent() {
        EventDispatcher.startListening(new LoginResultReceiver(this::receiveRequest));
    }

    private void sendRequest() {
        var uuid = Utilities.getUUID();
        String email = inpEmail.getText();
        String password = inpPassword.getText();

        //validate 
        if (!GlobalFunction.validateEmail(email)) {
            this.lableMessage.setText("Please enter correct email format");
            return;
        }

        if (!GlobalFunction.validatePass(password)) {
            this.lableMessage.setText("Password must contain at least 6 characters");
            return;
        }

        sender.send(null, new LoginRequest(uuid, email, password));
    }

    public void setInputFromRegister(String email, String password) {
        this.inpEmail.setText(email);
        this.inpPassword.setText(password);
    }

    private void receiveRequest(LoginResultRequest request) {
        System.out.println(request.result);

        if (request.result != LoginResultRequest.Result.SUCCESS) {
            this.lableMessage.setText(request.result.toString());
            return;
        }
        try {
            GlobalVariable.LoginAdminGUI.setVisible(false);
            GlobalVariable.main = new MainGUI();
            GlobalVariable.main.setVisible(true);
            //reset 
            GlobalVariable.tokenAdmin =null;
            GlobalVariable.clientList.removeAll(GlobalVariable.clientList);


        } catch (IOException ex) {
            Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        var token = request.token;
        GlobalVariable.tokenAdmin = request.token;

        var jwt = Jwts.parserBuilder().setSigningKey(JWTKey.getKey()).build().parseClaimsJws(GlobalVariable.tokenAdmin);
        GlobalVariable.idAdmin = jwt.getBody().get(LoginReceiver.ID_FIELD, Integer.class).toString();

        GlobalVariable.main.setIdAdmin();
//        var adminUuid = jwt.getBody().get(LoginReceiver.UUID_FIELD, String.class);

        new GetUserSender(ClientInstance.tcpClient).send(null, new GetUserRequest(GetUserRequest.Type.GET_ALL, token));

        new LoginAdminUdpSender(ClientInstance.udpClient).send(null, new LoginAdminUdpRequest(token));

//        var keyLogSender = new GetLogSender(ClientInstance.tcpClient);
//        var from = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
//        var to = new Date();
//        System.out.println(from);
//        System.out.println(to);

//        keyLogSender.send(null, new GetLogRequest("03000200-0400-0500-0006-000700080009", GlobalVariable.tokenAdmin, from, to));


//        new ProcessActionSender(ClientInstance.tcpClient).send(null, new ProcessActionRequestAdminSide(7012, ProcessActionRequestAdminSide.ProcessAction.KILL, token, "029B5DFC-C0AA-127C-26F5-50EBF6780955"));

//        new ChangeKeyLogConfigSender(ClientInstance.tcpClient).send(null, new ChangeKeyLogConfigRequest("029B5DFC-C0AA-127C-26F5-50EBF6780955", token, true, 60 * 60));

//        new GetLogSender(ClientInstance.tcpClient).send(null, new GetLogRequest("03000200-0400-0500-0006-000700080009", GlobalVariable.tokenAdmin, new Date(new Date().getTime() - 1000 * 60 * 60 * 24), new Date()));

//        new UserActionSender(ClientInstance.tcpClient).send(null, new UserActionRequestAdminSide("029B5DFC-C0AA-127C-26F5-50EBF6780955", token, UserActionRequestAdminSide.Action.LOG_OUT));
        //        new Thread(() -> {
//            while (true) {
//                var sender = new GetImageSender(ClientInstance.tcpClient);
//                var fps = 24;
////                sender.send(null, new GetImageRequestAdminSide(token, "029B5DFC-C0AA-127C-26F5-50EBF6780955"));
//
//                try {
//                    Thread.sleep(1000 / fps);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }).start();
//        var sender = new GetHardwareInfoSender(ClientInstance.tcpClient);
//        var hardwareTypes = new ArrayList<GetHardwareInfoAdminSide.HardwareType>();
//        hardwareTypes.add(GetHardwareInfoAdminSide.HardwareType.CPU);
//        hardwareTypes.add(GetHardwareInfoAdminSide.HardwareType.MEMORY);
//        hardwareTypes.add(GetHardwareInfoAdminSide.HardwareType.DISK);
//
//        sender.send(null, new GetHardwareInfoAdminSide(hardwareTypes, "029B5DFC-C0AA-127C-26F5-50EBF6780955", token));
//<<<<<<< HEAD

//        var sender = new GetProcessesSender(ClientInstance.tcpClient);
//        sender.send(null, new GetProcessesAdminSide(token, "03000200-0400-0500-0006-000700080009"));
//=======
//        var sender = new GetProcessesSender(ClientInstance.tcpClient);
//        sender.send(null, new GetProcessesAdminSide(token, "029B5DFC-C0AA-127C-26F5-50EBF6780955"));
//>>>>>>> origin/masterpr

//        var sender = new GetHardwareInfoSender(ClientInstance.tcpClient);
//        var hardwareTypes = new ArrayList<GetHardwareInfoAdminSide.HardwareType>();
//        hardwareTypes.add(GetHardwareInfoAdminSide.HardwareType.CPU);
//        hardwareTypes.add(GetHardwareInfoAdminSide.HardwareType.MEMORY);
//        hardwareTypes.add(GetHardwareInfoAdminSide.HardwareType.DISK);
//
//        sender.send(null, new GetHardwareInfoAdminSide(hardwareTypes, "029B5DFC-C0AA-127C-26F5-50EBF6780955", token));
//<<<<<<< HEAD
//
//        System.out.println("Change Name");
//
//        var changeNameSender = new ChangeUserNameSender(ClientInstance.tcpClient);
//        changeNameSender.send(null, new ChangeUserNameRequest(token, "029B5DFC-C0AA-127C-26F5-50EBF6780955", "Changed Name!!!"));
//=======
//>>>>>>> origin/master
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        inpEmail = new javax.swing.JTextField();
        forgetPassword = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lableMessage = new javax.swing.JLabel();
        inpPassword = new javax.swing.JPasswordField();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        wrapperRightPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        img = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMinimumSize(new java.awt.Dimension(200, 100));
        jPanel3.setPreferredSize(new java.awt.Dimension(400, 553));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setText("Get started now");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Enter your credentials to access your account.");

        inpEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(46, 79, 79)));
        inpEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpEmailActionPerformed(evt);
            }
        });
        inpEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpEmailKeyPressed(evt);
            }
        });

        forgetPassword.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        forgetPassword.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        forgetPassword.setText("Forgot password?");
        forgetPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forgetPasswordMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Email");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Password");

        btnLogin.setBackground(new java.awt.Color(46, 79, 79));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLogin.setText("Login");
        btnLogin.setOpaque(true);
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoginMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Don’t have an account? Sign up.");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        jLabel9.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new java.awt.Color(46, 79, 79)));

        lableMessage.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lableMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableMessage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lableMessageMouseClicked(evt);
            }
        });

        inpPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpPasswordMouseClicked(evt);
            }
        });
        inpPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpPasswordActionPerformed(evt);
            }
        });
        inpPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpPasswordKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(inpPassword)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpEmail)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(forgetPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                    .addComponent(lableMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(53, 53, 53))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(26, 26, 26)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inpEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(forgetPassword)
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(inpPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lableMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 782, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(49, 49, 49)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(264, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel3, java.awt.BorderLayout.LINE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(10, 553));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 782, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel4, java.awt.BorderLayout.LINE_END);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        wrapperRightPanel.setBackground(new java.awt.Color(46, 79, 79));
        wrapperRightPanel.setAlignmentX(10.0F);
        wrapperRightPanel.setOpaque(false);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("The simplest way to manage your system");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        img.setBackground(new java.awt.Color(255, 255, 255));
        img.setText("img");

        javax.swing.GroupLayout wrapperRightPanelLayout = new javax.swing.GroupLayout(wrapperRightPanel);
        wrapperRightPanel.setLayout(wrapperRightPanelLayout);
        wrapperRightPanelLayout.setHorizontalGroup(
            wrapperRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
            .addGroup(wrapperRightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        wrapperRightPanelLayout.setVerticalGroup(
            wrapperRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, wrapperRightPanelLayout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 25, Short.MAX_VALUE)
                .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(wrapperRightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(wrapperRightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, "card2");

        getContentPane().add(jPanel1, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inpEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpEmailActionPerformed

    private void btnLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseClicked
        sendRequest();
    }//GEN-LAST:event_btnLoginMouseClicked

    private void lableMessageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lableMessageMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lableMessageMouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // navigate to from register
        GlobalVariable.LoginAdminGUI.setVisible(false);
        GlobalVariable.RegisterAdminGUI.setVisible(true);

    }//GEN-LAST:event_jLabel7MouseClicked

    private void inpEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpEmailKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.inpPassword.requestFocus();
        }
    }//GEN-LAST:event_inpEmailKeyPressed

    private void forgetPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgetPasswordMouseClicked
        // TODO add your handling code here:
        GlobalVariable.LoginAdminGUI.setVisible(false);
        GlobalVariable.ForgotPassEmailGUI.setVisible(true);
    }//GEN-LAST:event_forgetPasswordMouseClicked

    private void inpPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpPasswordActionPerformed

    private void inpPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpPasswordMouseClicked

    }//GEN-LAST:event_inpPasswordMouseClicked

    private void inpPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpPasswordKeyPressed
        // TODO add your handling code here:
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            sendRequest();
        }
    }//GEN-LAST:event_inpPasswordKeyPressed

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
                new LoginGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnLogin;
    private javax.swing.JLabel forgetPassword;
    private javax.swing.JLabel img;
    private javax.swing.JTextField inpEmail;
    private javax.swing.JPasswordField inpPassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lableMessage;
    private javax.swing.JPanel wrapperRightPanel;
    // End of variables declaration//GEN-END:variables
}
