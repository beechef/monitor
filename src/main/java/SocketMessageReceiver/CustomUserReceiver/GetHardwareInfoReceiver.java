package SocketMessageReceiver.CustomUserReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.GetHardwareInfoAdminSide;
import SocketMessageReceiver.DataType.GetHardwareInfoServerSide;
import SocketMessageReceiver.DataType.GetHardwareInfoResultUserSide;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomUserSender.GetHardwareInfoResultSender;
import Utilities.Utilities;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OperatingSystem;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;

public class GetHardwareInfoReceiver extends SocketMessageReceiver<GetHardwareInfoServerSide> {

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_HARDWARE_INFO;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<GetHardwareInfoServerSide> socketMsg) {
        var hardwareTypes = socketMsg.msg.hardwareTypes;
        var hardwareInfos = new ArrayList<String>();

        for (var hardwareType : hardwareTypes) {
            var hardwareInfo = HardwareInfoFactory.getHardwareInfo(hardwareType);
            hardwareInfos.add(hardwareInfo.getHardwareInfo());
        }

        var sender = new GetHardwareInfoResultSender(server);
        sender.send(socketMsg.sender, new GetHardwareInfoResultUserSide(socketMsg.msg.id, hardwareInfos));
    }

    interface HardwareInfo {
        String getHardwareInfo();
    }

    public static class HardwareInfoFactory {
        public static HardwareInfo getHardwareInfo(GetHardwareInfoAdminSide.HardwareType hardwareType) {
            switch (hardwareType) {
                case CPU -> {
                    return new HardwareInfoCPU();
                }
                case MEMORY -> {
                    return new HardwareInfoMemory();
                }
                case DISK -> {
                    return new HardwareInfoDisk();
                }
                default -> {
                    return null;
                }
            }
        }
    }

    public static class HardwareInfoCPU implements HardwareInfo {
        private static long[] preTicks = new long[CentralProcessor.TickType.values().length];

        @Override
        public String getHardwareInfo() {
            var systemInfo = new SystemInfo();
            var hardwareAbstractionLayer = systemInfo.getHardware();
            var processor = hardwareAbstractionLayer.getProcessor();
            var operatingSystem = systemInfo.getOperatingSystem();

            var clockSpeed = getClockSpeed(processor);
            var usagePercent = getUsagePercent(processor);
            var logicalProcessorCount = getProcessorCount(processor);
            var physicalProcessorCount = getCoreCount(processor);
            var processCount = getProcessCount(operatingSystem);
            var threadCount = getThreadCount(operatingSystem);

            return "Clock Speed: " + clockSpeed + " GHz" +
                    "\nUsage Percent: " + usagePercent + "%" +
                    "\nLogical Processor Count: " + logicalProcessorCount +
                    "\nPhysical Processor Count: " + physicalProcessorCount +
                    "\nProcess Count: " + processCount +
                    "\nThread Count: " + threadCount +
                    "\n";
        }

        private double getUsagePercent(CentralProcessor processor) {
            var usagePercent = processor.getSystemCpuLoadBetweenTicks(preTicks) * 100;
            preTicks = processor.getSystemCpuLoadTicks();

            return usagePercent;
        }

        private double getClockSpeed(CentralProcessor processor) {
            return Utilities.hzToGHz(processor.getProcessorIdentifier().getVendorFreq());
        }

        private int getThreadCount(OperatingSystem operatingSystem) {
            return operatingSystem.getThreadCount();
        }

        private int getProcessCount(OperatingSystem operatingSystem) {
            return operatingSystem.getProcessCount();
        }

        private int getCoreCount(CentralProcessor processor) {

            return processor.getPhysicalProcessorCount();
        }

        private int getProcessorCount(CentralProcessor processor) {
            return processor.getLogicalProcessorCount();
        }
    }

    public static class HardwareInfoMemory implements HardwareInfo {
        @Override
        public String getHardwareInfo() {
            var systemInfo = new SystemInfo();
            var hardwareAbstractionLayer = systemInfo.getHardware();
            var memory = hardwareAbstractionLayer.getMemory();

            var totalMemory = Utilities.byteToGB(getTotalMemory(memory));
            var usedMemory = Utilities.byteToGB(getUsedMemory(memory));
            var availableMemory = Utilities.byteToGB(getAvailableMemory(memory));

            return "Total Memory: " + totalMemory + " GB" +
                    "\nUsed Memory: " + usedMemory + " GB" +
                    "\nAvailable Memory: " + availableMemory + " GB" +
                    "\n";
        }

        private long getAvailableMemory(GlobalMemory memory) {
            return memory.getAvailable();
        }

        private long getTotalMemory(GlobalMemory memory) {
            return memory.getTotal();
        }

        private long getUsedMemory(GlobalMemory memory) {
            return memory.getTotal() - memory.getAvailable();
        }
    }

    public static class HardwareInfoDisk implements HardwareInfo {
        @Override
        public String getHardwareInfo() {
            File[] disks = File.listRoots();

            var info = new StringBuilder();
            for (var disk : disks) {
                var diskName = getDiskName(disk);
                var diskTotalSize = getDiskTotalSize(disk);
                var diskUsedSize = getDiskUsedSize(disk);
                var diskAvailableSize = getDiskAvailableSize(disk);

                info.append("Disk Name: ").append(diskName).append("\n")
                        .append("Disk Total Size: ").append(diskTotalSize).append(" GB").append("\n")
                        .append("Disk Used Size: ").append(diskUsedSize).append(" GB").append("\n")
                        .append("Disk Available Size: ").append(diskAvailableSize).append(" GB").append("\n")
                        .append("\n");
            }

            return info.toString();
        }

        private String getDiskName(File disk) {
            return FileSystemView.getFileSystemView().getSystemDisplayName(disk);
        }

        private double getDiskTotalSize(File disk) {
            return Utilities.byteToGB(disk.getTotalSpace());
        }

        private double getDiskAvailableSize(File disk) {
            return Utilities.byteToGB(disk.getFreeSpace());
        }

        private double getDiskUsedSize(File disk) {
            return getDiskTotalSize(disk) - getDiskAvailableSize(disk);
        }

    }
}
