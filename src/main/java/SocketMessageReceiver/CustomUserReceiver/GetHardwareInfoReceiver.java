package SocketMessageReceiver.CustomUserReceiver;

import Server.Database.KeyPair;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.GetHardwareInfo.GetHardwareInfoAdminSide;
import SocketMessageReceiver.DataType.GetHardwareInfo.GetHardwareInfoServerSide;
import SocketMessageReceiver.DataType.GetHardwareInfoResult.GetHardwareInfoResultUserSide;
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
import java.util.HashMap;

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
        var hardwareInfos = new HashMap<GetHardwareInfoAdminSide.HardwareType, ArrayList<KeyPair<String, String>>>();

        for (var hardwareType : hardwareTypes) {
            var hardwareInfoGetter = HardwareInfoFactory.getHardwareInfo(hardwareType);
            var info = hardwareInfoGetter.getHardwareInfo();

            hardwareInfos.put(hardwareType, info);
        }

        var sender = new GetHardwareInfoResultSender(server);
        sender.send(socketMsg.sender, new GetHardwareInfoResultUserSide(socketMsg.msg.adminId, socketMsg.msg.adminUuid, hardwareInfos));
    }

    interface HardwareInfo {
        ArrayList<KeyPair<String, String>> getHardwareInfo();
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
        public static final String CLOCK_SPEED = "ClockSpeed";
        public static final String USAGE_PERCENT = "UsagePercent";
        public static final String LOGICAL_PROCESSOR_COUNT = "LogicalProcessorCount";
        public static final String PHYSICAL_PROCESSOR_COUNT = "PhysicalProcessorCount";
        public static final String PROCESS_COUNT = "ProcessCount";
        public static final String THREAD_COUNT = "ThreadCount";

        private static long[] preTicks = new long[CentralProcessor.TickType.values().length];

        @Override
        public ArrayList<KeyPair<String, String>> getHardwareInfo() {
            var info = new ArrayList<KeyPair<String, String>>();

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

            info.add(new KeyPair<>(CLOCK_SPEED, clockSpeed + " GHz"));
            info.add(new KeyPair<>(USAGE_PERCENT, usagePercent + " %"));
            info.add(new KeyPair<>(LOGICAL_PROCESSOR_COUNT, String.valueOf(logicalProcessorCount)));
            info.add(new KeyPair<>(PHYSICAL_PROCESSOR_COUNT, String.valueOf(physicalProcessorCount)));
            info.add(new KeyPair<>(PROCESS_COUNT, String.valueOf(processCount)));
            info.add(new KeyPair<>(THREAD_COUNT, String.valueOf(threadCount)));

            return info;
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
        public static final String TOTAL_MEMORY = "TotalMemory";
        public static final String USED_MEMORY = "UsedMemory";
        public static final String AVAILABLE_MEMORY = "AvailableMemory";

        @Override
        public ArrayList<KeyPair<String, String>> getHardwareInfo() {
            var info = new ArrayList<KeyPair<String, String>>();

            var systemInfo = new SystemInfo();
            var hardwareAbstractionLayer = systemInfo.getHardware();
            var memory = hardwareAbstractionLayer.getMemory();

            var totalMemory = Utilities.byteToGB(getTotalMemory(memory));
            var usedMemory = Utilities.byteToGB(getUsedMemory(memory));
            var availableMemory = Utilities.byteToGB(getAvailableMemory(memory));

            info.add(new KeyPair<>(TOTAL_MEMORY, totalMemory + " GB"));
            info.add(new KeyPair<>(USED_MEMORY, usedMemory + " GB"));
            info.add(new KeyPair<>(AVAILABLE_MEMORY, availableMemory + " GB"));

            return info;
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
        public static final String DISK_NAME = "DiskName";
        public static final String DISK_TOTAL_SIZE = "DiskTotalSize";
        public static final String DISK_USED_SIZE = "DiskUsedSize";
        public static final String DISK_AVAILABLE_SIZE = "DiskAvailableSize";

        public static final String SPACE = "Space";

        @Override
        public ArrayList<KeyPair<String, String>> getHardwareInfo() {
            File[] disks = File.listRoots();

            var info = new ArrayList<KeyPair<String, String>>();
            for (var disk : disks) {
                var diskName = getDiskName(disk);
                var diskTotalSize = getDiskTotalSize(disk);
                var diskUsedSize = getDiskUsedSize(disk);
                var diskAvailableSize = getDiskAvailableSize(disk);

                info.add(new KeyPair<>(DISK_NAME, diskName));
                info.add(new KeyPair<>(DISK_TOTAL_SIZE, diskTotalSize + " GB"));
                info.add(new KeyPair<>(DISK_USED_SIZE, diskUsedSize + " GB"));
                info.add(new KeyPair<>(DISK_AVAILABLE_SIZE, diskAvailableSize + " GB"));
                info.add(new KeyPair<>(SPACE, ""));
            }

            return info;
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
