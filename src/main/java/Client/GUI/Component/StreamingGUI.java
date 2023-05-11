/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Client.GUI.Component;

import Client.ClientInstance;
import Client.GUI.Lib.GlobalVariable;
import Client.GUI.Lib.RoundBorder;
import Server.EventDispatcher.EventDispatcher;
import SocketMessageReceiver.CustomAdminReceiver.GetImageResultReceiver;
import SocketMessageReceiver.DataType.GetImage.GetImageRequestAdminSide;
import SocketMessageReceiver.DataType.GetImage.GetImageResultServerSide;
import SocketMessageSender.CustomAdminSender.GetImageSender;
import Utilities.Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * @author Admin
 */
public class StreamingGUI extends javax.swing.JPanel {

    /**
     * Creates new form StreamingGUI
     */
    public static Thread streamingThread;
    public static boolean isStreaming = false;
    private HashMap<Thread, Boolean> flags = new HashMap<>();

    private GetImageResultServerSide currentImageReciver = null;
    private GetImageResultServerSide currentImageCapture = null;

    public StreamingGUI() {
        initComponents();
        //set jlable color
        this.LabelHeader.setForeground(GlobalVariable.primaryColor);
        //set border rounded

    }

    public void reset() {
        isStreaming = false;
        flags.put(streamingThread, false);

        streamingThread.interrupt();
        streamingThread = null;
        System.out.println("reset streming");
        panelStream.setVisible(false);

        removeEvent();
    }

    public void init() {
        isStreaming = true;
        panelStream.setVisible(true);

        listenEvent();

        if (streamingThread != null) {
            flags.put(streamingThread, false);

            streamingThread.interrupt();
            streamingThread = null;
        }

        streamingThread = new Thread(() -> {
            var fps = 24;
            var sender = new GetImageSender(ClientInstance.tcpClient);

            while (true) {
                var value = flags.get(streamingThread);

                if (value != null && !value || streamingThread != null && streamingThread.isInterrupted() || !isVisible()) {
                    return;
                }

                sender.send(null, new GetImageRequestAdminSide(GlobalVariable.tokenAdmin, GlobalVariable.selectedClientInfor.getID()));

                try {
                    Thread.sleep(1000 / fps);
                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
                    e.printStackTrace();
                    System.out.println("Streaming is interrupted");

                }
            }
        });

        streamingThread.start();
        flags.put(streamingThread, true);
    }

    private void listenEvent() {
        EventDispatcher.startListening(new GetImageResultReceiver(this::render));
    }

    private void removeEvent() {
        EventDispatcher.stopListening(new GetImageResultReceiver(this::render));
    }

    private final ArrayList<Byte> bytes = new ArrayList<>();

    synchronized private void render(GetImageResultServerSide data) {

        this.currentImageReciver = data;
        if (data.image.length == 0) {
            return;
        }

        for (var b : data.image) {
            bytes.add(b);
        }

        if (data.isEnd) {
            var imageBytes = new byte[bytes.size()];
            for (int i = 0; i < bytes.size(); i++) {
                imageBytes[i] = bytes.get(i);
            }

            var width = panelStream.getWidth();
            var height = panelStream.getHeight();
            try {
                var bais = new ByteArrayInputStream(imageBytes);
                var image = ImageIO.read(bais);

                panelStream.getGraphics().drawImage(image, 0, 0, width, height, null);

//                panelStream.validate();
//                panelStream.repaint();
                GlobalVariable.main.validate();
//                GlobalVariable.main.repaint();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.printf("Wrong Message");
            }
            bytes.clear();
        }
    }

    private void renderCapture() {
        GetImageResultServerSide data = currentImageReciver;
        currentImageCapture = currentImageReciver;

        if (data.image.length == 0) {
            return;
        }

        for (var b : data.image) {
            bytes.add(b);
        }

        if (data.isEnd) {
            var imageBytes = new byte[bytes.size()];
            for (int i = 0; i < bytes.size(); i++) {
                imageBytes[i] = bytes.get(i);
            }

            var width = panelPicture.getWidth();
            var height = panelPicture.getHeight();
            try {
                var bais = new ByteArrayInputStream(imageBytes);
                var image = ImageIO.read(bais);

                panelPicture.getGraphics().drawImage(image, 0, 0, width, height, null);

//                panelStream.validate();
//                panelStream.repaint();
                GlobalVariable.main.validate();
//                GlobalVariable.main.repaint();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.printf("Wrong Message");
            }
            bytes.clear();
        }
    }

    private void saveImage() {
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showSaveDialog(panelPicture);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filename = GlobalVariable.currentPath + "/src/main/java/LocalStorageApp/" + GlobalVariable.selectedClientInfor.getID() + ".png"; // đường dẫn của tập tin
            File file = new File(filename);

            int i = 1;
            while (file.exists()) {
                filename = "LocalStorageApp/" + GlobalVariable.selectedClientInfor.getID() + i + ".png";
                file = new File(filename);
                i++;
            }

            try {
                BufferedImage image = new BufferedImage(panelPicture.getWidth(), panelPicture.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics2D = image.createGraphics();
                panelPicture.print(graphics2D);
                ImageIO.write(image, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to save image.");
            }
        }
    }

    private void saveImage(String path, String fileName) {

        if (currentImageCapture == null) {
            return;
        }
        if (currentImageCapture.image.length == 0) {
            return;
        }

        byte[] imageBytes = new byte[currentImageCapture.image.length];
        for (int i = 0; i < currentImageCapture.image.length; i++) {
            imageBytes[i] = currentImageCapture.image[i];
        }

        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(bais);

            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdir();
            }

            File file = new File(path + "/" + fileName + ".png");
            int i = 1;
            while (file.exists()) {
                file = new File(path + "/" + fileName + "-" + i + ".png");
                i++;
            }

            ImageIO.write(image, "png", file);

            JOptionPane.showMessageDialog(GlobalVariable.main, "Save imgae success");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("Wrong Message");
            JOptionPane.showMessageDialog(GlobalVariable.main, "Save imgae falure");

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. Th e content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LabelHeader = new javax.swing.JLabel();
        panelStream = new javax.swing.JPanel();
        panelPicture = new javax.swing.JPanel();
        btnCapture = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        LabelHeader.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        LabelHeader.setText("Streaming");

        panelStream.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout panelStreamLayout = new javax.swing.GroupLayout(panelStream);
        panelStream.setLayout(panelStreamLayout);
        panelStreamLayout.setHorizontalGroup(
                panelStreamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        panelStreamLayout.setVerticalGroup(
                panelStreamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 347, Short.MAX_VALUE)
        );

        panelPicture.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout panelPictureLayout = new javax.swing.GroupLayout(panelPicture);
        panelPicture.setLayout(panelPictureLayout);
        panelPictureLayout.setHorizontalGroup(
                panelPictureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 522, Short.MAX_VALUE)
        );
        panelPictureLayout.setVerticalGroup(
                panelPictureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        btnCapture.setBackground(new java.awt.Color(46, 79, 79));
        btnCapture.setForeground(new java.awt.Color(255, 255, 255));
        btnCapture.setText("Capture");
        btnCapture.setToolTipText("");
        btnCapture.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCaptureMouseClicked(evt);
            }
        });
        btnCapture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaptureActionPerformed(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(20, 108, 148));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save");
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
        });
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(LabelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(134, 134, 134)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(panelStream, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(panelPicture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnCapture, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(160, 160, 160))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(LabelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelStream, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(panelPicture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnCapture, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 127, Short.MAX_VALUE)))
                                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCaptureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaptureActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCaptureActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCaptureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCaptureMouseClicked
        // TODO add your handling code here:

        renderCapture();
    }//GEN-LAST:event_btnCaptureMouseClicked

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked
        // TODO add your handling code here:
        System.out.println("save img");
        saveImage(GlobalVariable.currentPath + "/src/main/java/LocalStorageApp", GlobalVariable.selectedClientInfor.getID());
    }//GEN-LAST:event_btnSaveMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelHeader;
    private javax.swing.JButton btnCapture;
    private javax.swing.JButton btnSave;
    private javax.swing.JPanel panelPicture;
    private javax.swing.JPanel panelStream;
    // End of variables declaration//GEN-END:variables

}
