package Client;

import Client.GUI.Admin.ForgotPasswordAdminGUI;
import Client.GUI.Admin.ForgotPasswordAdminIpEmailGUI;
import Client.GUI.Admin.LoginGUI;
import Client.GUI.Admin.RegisterGUI;
import Client.GUI.Lib.ClientDTO;
import Client.GUI.Lib.DiskDTO;
import Client.GUI.Lib.GlobalVariable;
import Client.GUI.Lib.HardwareDTO;
import Client.GUI.Lib.ProcessDTO;
import Server.EventDispatcher.EventDispatcher;
import SocketMessageReceiver.CustomAdminReceiver.*;
import SocketMessageReceiver.CustomUserReceiver.GetHardwareInfoReceiver;
import SocketMessageReceiver.DataType.*;
import SocketMessageSender.CustomAdminSender.GetLogSender;
import SocketMessageSender.CustomAdminSender.LogOutAdminSender;
import SocketMessageSender.CustomAdminSender.UserActionSender;
import SocketMessageSender.CustomUserSender.LogOutUserSender;
import jdk.jfr.Event;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

public class Admin {

    public static void main(String[] args) throws IOException, InterruptedException {
        var serverInfo = getServerInfo();
        if (serverInfo == null) {
            System.out.println("Invalid server info");
            return;
        }

        var host = serverInfo.host;
        var port = serverInfo.port;

        var tcpClient = new TCPClient(host, port);
        tcpClient.setBuffer(2048 * 1024);
        tcpClient.start();

        var udpClient = new UDPClient("localhost", 4446);
        udpClient.setBuffer(2048 * 1024);
        udpClient.start();

        ClientInstance.tcpClient = tcpClient;
        ClientInstance.udpClient = udpClient;

        GlobalVariable.LoginAdminGUI = new LoginGUI();
        GlobalVariable.RegisterAdminGUI = new RegisterGUI();
        GlobalVariable.ForgotPasswordAdminGUi = new ForgotPasswordAdminGUI();
        GlobalVariable.ForgotPassEmailGUI = new ForgotPasswordAdminIpEmailGUI();
        GlobalVariable.LoginAdminGUI.setVisible(true);

//        java.awt.EventQueue.invokeLater(() -> new LoginGUI().setVisible(true));
        EventDispatcher.startListening(new GetUserResultReceiver(data -> {
            System.out.println("load user");
            for (var user : data.userInfos) {

                boolean stmpStatus = false;
                if (user.status.toString().equals("AVAILABLE")) {
                    stmpStatus = true;
                }
                GlobalVariable.clientList.add(new ClientDTO(user.name, user.uuid, user.host, stmpStatus, user.port, user.writeLogInterval, user.isWriteLog));
            }
            GlobalVariable.listClient.renderTable(GlobalVariable.clientList);

        }));

        EventDispatcher.startListening(new LoginUserResultReceiver(data -> {
            System.out.println("user login");
            var userInfo = data.userInfo;

            GlobalVariable.listClient.handleUerLogin(userInfo.name, userInfo.uuid, userInfo.host, true, userInfo.port, userInfo.writeLogInterval, userInfo.isWriteLog);

        }));

        EventDispatcher.startListening(new GetHardwareInfoResultReceiver(data -> {
            GlobalVariable.hardwareData = new HardwareDTO();
            for (var hardwareInfo : data.hardwareInfos.entrySet()) {
                System.out.println("Hardware name: " + hardwareInfo.getKey());

                if (hardwareInfo.getKey().toString().equals("CPU")) {
                    for (var info : hardwareInfo.getValue()) {
                        System.out.println("Key: " + info.key);
                        System.out.println("Value: " + info.value);

                        switch (info.key) {
                            case GetHardwareInfoReceiver.HardwareInfoCPU.CLOCK_SPEED -> {
                                GlobalVariable.hardwareData.setCpuClockSpeed(info.value);
                            }
                            case GetHardwareInfoReceiver.HardwareInfoCPU.USAGE_PERCENT -> {
                                GlobalVariable.hardwareData.setCpuUsagePercent(info.value);
                            }
                            case GetHardwareInfoReceiver.HardwareInfoCPU.LOGICAL_PROCESSOR_COUNT -> {
                                GlobalVariable.hardwareData.setCpuLogicalProcessorCount(info.value);
                            }
                            case GetHardwareInfoReceiver.HardwareInfoCPU.PHYSICAL_PROCESSOR_COUNT -> {
                                GlobalVariable.hardwareData.setCpuPhysicalProcessorCount(info.value);
                            }
                            case GetHardwareInfoReceiver.HardwareInfoCPU.PROCESS_COUNT -> {
                                GlobalVariable.hardwareData.setCpuProcessCount(info.value);
                            }
                            case GetHardwareInfoReceiver.HardwareInfoCPU.THREAD_COUNT -> {
                                GlobalVariable.hardwareData.setCpuThreadCount(info.value);
                            }
                        }
                    }
                }

                if (hardwareInfo.getKey().toString().equals("MEMORY")) {
                    for (var info : hardwareInfo.getValue()) {
                        System.out.println("Key: " + info.key);
                        System.out.println("Value: " + info.value);

                        switch (info.key) {
                            case GetHardwareInfoReceiver.HardwareInfoMemory.TOTAL_MEMORY -> {
                                GlobalVariable.hardwareData.setTotalMemory(info.value);
                            }
                            case GetHardwareInfoReceiver.HardwareInfoMemory.USED_MEMORY -> {
                                GlobalVariable.hardwareData.setUsedMemory(info.value);
                            }
                            case GetHardwareInfoReceiver.HardwareInfoMemory.AVAILABLE_MEMORY -> {
                                GlobalVariable.hardwareData.setAvailableMemory(info.value);
                            }
                        }
                    }
                }

                if (hardwareInfo.getKey().toString().equals("DISK")) {

                    String DiskName = null;
                    String DiskTotalSize = null;
                    String DiskUsedSize = null;
                    String DiskAvailableSize = null;
                    String Space = null;

                    for (var info : hardwareInfo.getValue()) {
                        System.out.println("Key: " + info.key);
                        System.out.println("Value: " + info.value);

                        if (info.key.equals("DiskName")) {
                            DiskName = info.value;
                        } else {
                            if (info.key.equals("DiskTotalSize")) {
                                DiskTotalSize = info.value;
                            } else {
                                if (info.key.equals("DiskUsedSize")) {
                                    DiskUsedSize = info.value;
                                } else {
                                    if (info.key.equals("DiskAvailableSize")) {
                                        DiskAvailableSize = info.value;
                                    } else {
                                        if (info.key.equals("Space")) {
                                            Space = info.value;
                                            DiskDTO x = new DiskDTO(DiskName, DiskTotalSize, DiskUsedSize, DiskAvailableSize, Space);
                                            GlobalVariable.hardwareData.Disks.add(x);
                                            DiskName = null;
                                            DiskTotalSize = null;
                                            DiskUsedSize = null;
                                            DiskAvailableSize = null;
                                            Space = null;
                                        }
                                    }
                                }
                            }
                        }

                    }

                }
            }
            //
            GlobalVariable.hardware.renderContent();
        }
        ));

        EventDispatcher.startListening(
                new GetProcessesResultReceiver(data -> {
                    GlobalVariable.processList.removeAll(GlobalVariable.processList);
                    for (var process : data.processes) {
                        GlobalVariable.processList.add(new ProcessDTO(process.name, process.id, process.path));
                    }
                    GlobalVariable.process.renderProcess(GlobalVariable.processList);
                }
                ));

        EventDispatcher.startListening(
                new ChangeUserNameResultReceiver(data -> {
                    System.out.println("Change user name result:");
                    System.out.println("UUID: " + data.uuid);
                    System.out.println("Before name: " + data.beforeName);
                    System.out.println("After name: " + data.afterName);
                    System.out.println();
                    GlobalVariable.listClient.changedName(data.uuid, data.afterName);
                }
                ));

        EventDispatcher.startListening(
                new ProcessActionResultReceiver(data -> {
                    JOptionPane.showMessageDialog(GlobalVariable.main, "Endtask " + data.processId + " " + data.result + "  " + data.message);

                    GlobalVariable.main.sendRequestGetAllProcess();

                    System.out.println("Process action result:");
                    System.out.println("Process ID: " + data.processId);
                    System.out.println("Action: " + data.action);
                    System.out.println("Result: " + data.result);
                    System.out.println("Message: " + data.message);
                    System.out.println();
                }
                ));

        EventDispatcher.startListening(
                new LogOutUserReceiver(data -> {
                    System.out.println("Log out user result:");
                    System.out.println("UUID: " + data.deviceId);
                    System.out.println();

                    GlobalVariable.listClient.handleUerLogout(data.deviceId);

                }
                ));

        EventDispatcher.startListening(
                new ForgetPasswordResultReceiver(data -> {
                    System.out.println("Forget password result:");
                    System.out.println("Email: " + data.email);
                    System.out.println("Result: " + data.result);
                    System.out.println();
                }
                ));

        EventDispatcher.startListening(
                new ChangeForgetPasswordResultReceiver(data -> {
                    System.out.println("Change forget password result:");
                    System.out.println("Result: " + data.result);
                    System.out.println();
                }
                ));

        EventDispatcher.startListening(
                new ChangeKeyLogConfigResultReceiver(data -> {
                    JOptionPane.showMessageDialog(GlobalVariable.main, "Changed keylog config");

                    GlobalVariable.selectedClientInfor.setIsWriteLog(data.isWriteLog);
                    GlobalVariable.selectedClientInfor.setWriteLogInterval(data.writeLogInterval);

                    GlobalVariable.clientList.forEach(e -> {
                        if (e.getID() == data.uuid) {
                            e.setIsWriteLog(data.isWriteLog);
                            e.setWriteLogInterval(data.writeLogInterval);
                        }
                    });

                    GlobalVariable.listClient.renderTable(GlobalVariable.clientList);

                    System.out.println("Change KeyLog config result:");
                    System.out.println("Result: " + data.uuid);
                    System.out.println("Write Log: " + data.isWriteLog);
                    System.out.println("Interval: " + data.writeLogInterval);
                    System.out.println();
                }
                ));

        EventDispatcher.startListening(
                new GetLogResultReceiver(data -> {
                    System.out.println("Get log result:");
                    System.out.println("Result: " + data.log);
                    System.out.println("Is end: " + data.isEnd);
                    System.out.println();
                    GlobalVariable.others.renderKeylogReciver(data.log);
                }
                ));

        EventDispatcher.startListening(
                new UserActionResultReceiver((data -> {
                    System.out.println("User action result:");
                    System.out.println("Action: " + data.action);
                    System.out.println("Result: " + data.result);
                    System.out.println("Message: " + data.message);
                    System.out.println();
                })));

        var shutdownThread = new Thread(() -> {
            var sender = new LogOutAdminSender(tcpClient);
            sender.send(null, new LogOutAdminRequest(GlobalVariable.tokenAdmin));
        });

        Runtime.getRuntime()
                .addShutdownHook(shutdownThread);
        Thread.currentThread()
                .join();
    }

    private static final String SERVER_INFO_FILE = "admin_server_info.dat";

    private static ServerInfo getServerInfo() {
        File file = new File("./" + SERVER_INFO_FILE);
        if (!file.exists()) {
            return null;
        }

        try (var reader = new BufferedReader(new FileReader(file))) {
            var serverIp = reader.readLine();
            var serverPort = Integer.parseInt(reader.readLine());

            return new ServerInfo(serverIp, serverPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
